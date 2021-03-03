package de.fsu.Sword2Tester;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import java.util.stream.Stream;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swordapp.client.AuthCredentials;
import org.swordapp.client.CollectionEntries;
import org.swordapp.client.ProtocolViolationException;
import org.swordapp.client.SWORDClient;
import org.swordapp.client.SWORDClientException;
import org.swordapp.client.SWORDCollection;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger();

    public static Stream<org.apache.abdera.model.Entry> getCollectionEntryStream(SWORDCollection col, SWORDClient client, AuthCredentials auth) throws SWORDClientException, ProtocolViolationException {
        IRI href = col.getHref();
        CollectionEntries listCollection = client.listCollection(col, auth);
        final Feed feed = listCollection.getFeed();

        FeedEntryWalker feedEntryWalker = new FeedEntryWalker(feed, client, auth);

        final String lastURL = feed.getLink("last").getHref().toString();
        final String[] urlParts = lastURL.split("/");
        final String page = urlParts[urlParts.length - 1];
        final int lastPage = Integer.parseInt(page, 10);
        final int estEntries = lastPage * feed.getEntries().size();

        LOGGER.info("Stream feed entrys. Estimated entries: " + estEntries);

        return Stream.generate(() -> {
            return feedEntryWalker.getNextEntry();
        })
                .limit(estEntries)
                .filter(s -> s.isPresent())
                .map(s -> s.get());
    }

    private static class FeedEntryWalker {

        public FeedEntryWalker(Feed feed, SWORDClient client, AuthCredentials auth) {
            this.current = this.start = feed;
            this.client = client;
            this.auth = auth;
            this.currentIt = this.current.getEntries().listIterator();
        }

        private final SWORDClient client;
        private final AuthCredentials auth;

        private Feed current;
        private final Feed start;
        private ListIterator<Entry> currentIt;

        public synchronized Optional<org.apache.abdera.model.Entry> getNextEntry() {
            if (!this.currentIt.hasNext()) {
                Optional<Feed> nextFeed = getNextFeed();

                if (!nextFeed.isPresent()) {
                    return Optional.empty();
                } else {
                    this.current = nextFeed.get();
                    this.currentIt = this.current.getEntries().listIterator();
                }

                if (!this.currentIt.hasNext()) {
                    return Optional.empty();
                }
            }

            return Optional.of(this.currentIt.next());
        }

        private Optional<Feed> getNextFeed() {
            List<Link> links = current.getLinks("next");
            if (links.isEmpty()) {
                return Optional.empty();
            } else {
                return links.stream()
                        .map((Link link) -> {
                            try {
                                return client.listCollection(link.getHref().toString(), this.auth).getFeed();
                            } catch (SWORDClientException | ProtocolViolationException ex) {
                                return null;
                            }
                        })
                        .filter(s -> s != null)
                        .findFirst();
            }
        }
    }

    public static AuthCredentials getDefaultAuth(){
        return new AuthCredentials("administrator", "alleswirdgut", null,null,null);
    }
}

package com.bandsintown.activityfeed;

import com.bandsintown.activityfeed.interfaces.SpotifyPreviewLinkProcessor;
import com.bandsintown.activityfeed.objects.AudioPreviewInfo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class SpotifyPreviewLinkProcessorUnitTest {

    private static final String TRACK_ID = "37FXw5QGFN7uwwsLy8uAc0";
    private static final String ARTIST_ID = "47FXw5QGFN7uwwsLy8uAc0";
    private static final String ALBUM_ID = "57FXw5QGFN7uwwswernERS";
    private static final String TRACK_URL = "https://open.spotify.com/track/" + TRACK_ID;
    private static final String ARTIST_URL = "https://open.spotify.com/artist/" + ARTIST_ID;
    private static final String ALBUM_URL = "https://open.spotify.com/album/" + ALBUM_ID;

    @Test
    public void spotify_regex() throws Exception {
        SpotifyPreviewLinkProcessor linkProcessor = new SpotifyPreviewLinkProcessor();
        AudioPreviewInfo trackInfo = linkProcessor.process("Bla bla bla " + TRACK_URL + " bla bla bla");
        AudioPreviewInfo artistInfo = linkProcessor.process("Bla bla bla " + ARTIST_URL + " bla bla bla");
        AudioPreviewInfo albumInfo = linkProcessor.process("Bla bla bla " + ALBUM_URL + " bla bla bla");

        assertEquals(TRACK_URL, trackInfo.getUrlInfoWasGeneratedFrom());
        assertEquals(TRACK_ID, trackInfo.getId());
        assertEquals("track", trackInfo.getType());
        assertEquals(FeedValues.SPOTIFY, trackInfo.getSource());

        assertEquals(ARTIST_URL, artistInfo.getUrlInfoWasGeneratedFrom());
        assertEquals(ARTIST_ID, artistInfo.getId());
        assertEquals("artist", artistInfo.getType());
        assertEquals(FeedValues.SPOTIFY, artistInfo.getSource());

        assertEquals(ALBUM_URL, albumInfo.getUrlInfoWasGeneratedFrom());
        assertEquals(ALBUM_ID, albumInfo.getId());
        assertEquals("album", albumInfo.getType());
        assertEquals(FeedValues.SPOTIFY, albumInfo.getSource());

        AudioPreviewInfo comboInfo = linkProcessor.process("Bla bla bla " + ALBUM_URL + " bla " + ARTIST_URL);

        assertEquals(ARTIST_URL, comboInfo.getUrlInfoWasGeneratedFrom());
        assertEquals(ARTIST_ID, comboInfo.getId());
        assertEquals("artist", comboInfo.getType());
        assertEquals(FeedValues.SPOTIFY, comboInfo.getSource());
    }
}
package com.example.modules.entertainment_ecosystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserProfileSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.SubscriptionSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.VideoGameSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumThreadSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.ForumPostSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.AchievementSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.OnlineEventSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.MusicTrackSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.PlaylistSimpleDto;

import com.example.modules.entertainment_ecosystem.dtosimple.UserWalletSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private Date registrationDate;

    private String country;

    private String profilePictureUrl;

    private Date lastActiveDate;

    private String userStatus;

    private String bio;

    private List<ReviewSimpleDto> reviews;

    private List<MovieSimpleDto> watchlistMovies;

    private List<ArtistSimpleDto> favoriteArtists;

    private List<UserProfileSimpleDto> followedUsers;

    private List<UserProfileSimpleDto> followingUsers;

    private List<GenreSimpleDto> favoriteGenres;

    private List<SubscriptionSimpleDto> subscriptions;

    private List<EpisodeSimpleDto> watchedEpisodes;

    private List<VideoGameSimpleDto> playedGames;

    private List<ForumThreadSimpleDto> forumThreads;

    private List<ForumPostSimpleDto> forumPosts;

    private List<AchievementSimpleDto> achievements;

    private List<OnlineEventSimpleDto> hostedOnlineEvents;

    private List<OnlineEventSimpleDto> attendedOnlineEvents;

    private List<MerchandiseSimpleDto> ownedMerchandise;

    private List<PodcastSimpleDto> libraryPodcasts;

    private List<MusicTrackSimpleDto> listenedMusic;

    private List<PlaylistSimpleDto> playlists;

    private UserWalletSimpleDto wallet;

}
package com.example.modules.entertainment_ecosystem.dtosimple;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

    import java.util.Date;

import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.model.Artist;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoGameSimpleDto {

    private Long id;

    private String title;

    private Date releaseDate;

    private Artist developer;

    private Publisher publisher;

    private String platform;

}
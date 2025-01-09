package com.ligg.pojo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeData {
    private String animeId;
    private String title;
    private String description;//动漫描述
    private String coverImage;
    private LocalDate releaseDate;//首播时间
    private String status;//动漫状态
    private Long episodeId;
    private String episodeTitle;//集标题
    private LocalDate airDate;//集首播时间
    private Integer duration;//集时长
    private Integer episodeNumber;//集序号
    private String episodeImage;//剧集封面
}

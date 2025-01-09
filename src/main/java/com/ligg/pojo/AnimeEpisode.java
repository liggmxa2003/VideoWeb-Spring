package com.ligg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeEpisode {
    private Long episodeId;
    private Long animeId;//动漫id
    private String episodeTitle;//标题
    private Date airDate;//上映时间
    private Integer duration;//时长
    private Integer episodeNumber;//集数
    private String episodeImage;//封面
    private String episodeVideo;//播放地址
}

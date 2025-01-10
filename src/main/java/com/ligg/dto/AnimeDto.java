package com.ligg.dto;

import com.ligg.pojo.AnimeEpisode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDto {
    private Long animeId;
    private String title;//标题
    private String coverImage;//封面
    private String description;//描述
    private LocalDate releaseDate;//首播日期
    private String status;//状态
    private List<AnimeEpisode> episodes;//剧集

}

package com.ligg;

import com.github.pagehelper.Page;
import com.ligg.mapper.user.UserVideoMapper;
import com.ligg.pojo.PageBean;
import com.ligg.pojo.Video;
import com.ligg.service.impl.user.UserVideoServiceImpl;
import com.ligg.utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserVideoTest {
    @Mock
    private UserVideoMapper userVideoMapper;

    @InjectMocks
    private UserVideoServiceImpl userVideoService;
}




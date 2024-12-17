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

    @BeforeEach
    public void setUp() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void list_ValidInputs_ReturnsCorrectPageBean() {
        try (MockedStatic<ThreadLocalUtil> threadLocalUtilMockedStatic = Mockito.mockStatic(ThreadLocalUtil.class)) {
            // Arrange
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", 1);
            threadLocalUtilMockedStatic.when(ThreadLocalUtil::get).thenReturn(userMap);

            Page<Video> page = new Page<>();
            page.add(new Video(1, "title", "cover", 1, "content", LocalDateTime.now(), LocalDateTime.now()));
            page.add(new Video(2, "title", "cover", 1, "content", LocalDateTime.now(), LocalDateTime.now()));
            page.setPageNum(1);
            page.setPageSize(10);
            page.setTotal(2);

            when(userVideoMapper.list(eq(1), eq(null), eq("state"))).thenReturn(page.getResult());

            // Act
            PageBean<Video> result = userVideoService.list(1, 10, null, "state");

            // Assert
            assertEquals(2, result.getTotal());
            assertEquals(2, result.getItems().size());
            assertEquals(1, result.getItems().get(0).getId());
            assertEquals(1, result.getItems().get(1).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.managereports.controller;

import com.managereports.service.ManageReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Raouf Bouklab
 * Since 27-10-2019
 */
@RunWith(SpringRunner.class)
public class ManageReportControllerTest {

    private static final String FILE_NAME = "paragraph.txt";
    private static final String PARAGRAPH = "Test paragraph";

    @MockBean
    private ManageReportService manageReportService;

    private MockMvc mockMvc;

    private ManageReportController manageReportController;

    private Map<String, Integer> wordOccurrence;

    @Before
    public void setup() {
        manageReportController = new ManageReportController();
        manageReportController.setManageReportService(manageReportService);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(manageReportController)
                .setViewResolvers(viewResolver)
                .build();
        given(manageReportService.getFileName()).willReturn(FILE_NAME);
        given(manageReportService.readParagraphFromFile(any())).willReturn(PARAGRAPH);
        wordOccurrence = new HashMap<>();
        wordOccurrence.put("Test", 1);
        wordOccurrence.put("paragraph", 1);
        given(manageReportService.getSortedWordsOccurrences()).willReturn(wordOccurrence);
    }

    @Test
    public void testGetReport() throws Exception {
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andExpect(view().name("report"))
                .andExpect(model().attribute("fileName", FILE_NAME))
                .andExpect(model().attribute("paragraph", PARAGRAPH))
                .andExpect(model().attribute("wordOccurrencesMap", wordOccurrence));
    }
}
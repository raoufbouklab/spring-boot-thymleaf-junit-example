package com.managereports.controller;

import com.managereports.service.ManageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Raouf Bouklab
 * Since 26-10-2019
 */
@Controller
public class ManageReportController {

    @Autowired
    private ManageReportService manageReportService;

    /**
     * Generate Report
     * @param model
     * @return report.html
     */
    @GetMapping("/report")
    public String getReport(Model model){
        String filename = manageReportService.getFileName();
        model.addAttribute("fileName", filename);
        model.addAttribute("paragraph", manageReportService.readParagraphFromFile(filename));
        model.addAttribute("wordOccurrencesMap", manageReportService.getSortedWordsOccurrences());

        return "report";
    }

    /**
     *
     * @return about.html page
     */
    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }

    /**
     *
     * @return home.html page
     */
    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    /**
     *
     * @return contact.html page
     */
    @GetMapping("/contact")
    public String getContact(){
        return "contact";
    }

    /**
     * Setter for the manageReportService
     *
     * @param manageReportService
     */
    public void setManageReportService(ManageReportService manageReportService) {
        this.manageReportService = manageReportService;
    }
}

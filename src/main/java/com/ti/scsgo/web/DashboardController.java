package com.ti.scsgo.web;

import com.ti.scsgo.domain.EngineRun;
import com.ti.scsgo.domain.Trend;
import com.ti.scsgo.service.EngineRunService;
import com.ti.scsgo.service.FileUploadService;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Jonas
 */
@Controller
public class DashboardController {
    
    private List<EngineRun> runEngine;
    
    Logger LOG = LoggerFactory.getLogger(DashboardController.class);
    
    @Autowired
    protected FileUploadService fs;
    @Autowired
    protected EngineRunService es;
    
    @GetMapping("/")
    public String home() throws IOException {
        reset();
        return "index";
    }
    
    @GetMapping("/chart")
    public String chart() throws IOException {
        return "charts";
    }
    
    @PostMapping("/upload")
    @ResponseBody
    public String upload(
            MultipartFile file) throws IOException {
        System.out.println("file = " + file.getOriginalFilename());
        fs.saveStream(file);
        // run engine when loaded
        runEngine = es.runEngine();
        return "SUCCESS";
    }
    
    @GetMapping("/report")
    @ResponseBody
    public String report(Model model) {
        return "report";
    }
    
    @GetMapping("/test")
    public String test(Model model) throws IOException {
        checkEngine();
        model.addAttribute("runCount", runEngine.size());
        return "test";
    }
    
    @GetMapping("/getData/{id}")
    @ResponseBody
    public EngineRun getRunData(@PathVariable int id) throws IOException {
        checkEngine();
        return runEngine.get(id);
    }
    
    @GetMapping("/getTable/{id}")
    @ResponseBody
    public Map<String, List<List<String>>> getTable(@PathVariable int id) throws IOException {
        checkEngine();
        final EngineRun engineRun = runEngine.get(id);
        Map<String, List<List<String>>> table = new HashMap<>();
        
        final NumberFormat fmt = new DecimalFormat("#0.00");
        final List<List<String>> rows = engineRun.getGroupSetups()
                .stream()
                .map(grpSetup -> {
                    List<String> str = new ArrayList<>();
                    str.add(grpSetup.getName());
                    str.add(fmt.format(grpSetup.getDemand()));
                    str.add(fmt.format(grpSetup.getEquipments()));
                    str.add(fmt.format(grpSetup.getPPH()));
                    str.add(fmt.format(grpSetup.getEPP()));
                    str.add(fmt.format(grpSetup.getManpower()));
                    str.add(fmt.format(grpSetup.getEquipmentUtilization()));
                    str.add(fmt.format(grpSetup.getTotalOutput()));
                    str.add(fmt.format(grpSetup.getDemandSatisfaction()));
                    str.add(fmt.format(grpSetup.getExcessManpower()));
                    return str;
                }).collect(Collectors.toList());
        
        table.put("data", rows);
        return table;
    }
    
    @GetMapping("/getTrends")
    @ResponseBody
    public List<Trend> getTrends() throws IOException {
        checkEngine();
        return runEngine.stream().map(
                x -> {
                    final Trend trend = new Trend();
                    trend.setTotalDemand(x.getTotalDemand());
                    trend.setTotalOutput(x.getTotalOut());
                    trend.setWeek(x.getDateStr());
                    return trend;
                }).collect(Collectors.toList());
    }
    
    @GetMapping("/reset")
    @ResponseBody
    public String reset() throws IOException {
        if (runEngine != null) {
            runEngine.clear();
            runEngine = null;
        }
        fs.clearTmp();
        return "SUCCESS";
    }
    
    private void checkEngine() throws IOException {
        // check if we have, else rerun engine
        if (runEngine == null) {
            runEngine = es.runEngine();
        }
    }
}

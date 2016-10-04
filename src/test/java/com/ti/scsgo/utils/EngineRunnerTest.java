/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ti.scsgo.utils;

import com.ti.sc.scsgo.GroupSetup;
import com.ti.scsgo.domain.EngineRun;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author a0284021
 */
public class EngineRunnerTest {

    /**
     * Test of run method, of class EngineRunner.
     */
    @Test
    public void testRun() throws Exception {
        File file = new File("sample data/100916-PHI.csv");
        EngineRun result = EngineRunner.run(file);
        System.out.println("result= " + result);
        
    }

}

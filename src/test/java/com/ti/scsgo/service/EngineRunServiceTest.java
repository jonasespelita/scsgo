package com.ti.scsgo.service;

import com.ti.scsgo.domain.EngineRun;
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
public class EngineRunServiceTest {

    /**
     * Test of runEngine method, of class EngineRunService.
     */
    @Test
    public void testRunEngine() throws Exception {
        EngineRunService instance = new EngineRunService();
        final List<EngineRun> runEngine = instance.runEngine();
        assertFalse(runEngine.isEmpty());
        for (EngineRun engineRun : runEngine) {
            System.out.println("engineRun = " + engineRun);
        }
    }

}

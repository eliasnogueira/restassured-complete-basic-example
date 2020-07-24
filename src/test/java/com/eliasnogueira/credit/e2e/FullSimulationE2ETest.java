package com.eliasnogueira.credit.e2e;

import com.eliasnogueira.credit.client.RestrictionsClient;
import com.eliasnogueira.credit.client.SimulationsClient;
import org.testng.annotations.Test;

public class FullSimulationE2ETest {

    @Test(groups = "e2e")
    public void completeSimulation() {
        new RestrictionsClient().queryRestrictionAndReturnNotFound();
        new SimulationsClient().submitSuccessfulSimulation();
    }
}

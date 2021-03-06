package edu.iris.dmc.station.conditions;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.iris.dmc.DocumentMarshaller;
import edu.iris.dmc.fdsn.station.model.FDSNStationXML;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.RuleEngineServiceTest;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.Result;

public class StationEpochOverlapConditionTest {

	private FDSNStationXML theDocument;

	@Before
	public void init() throws Exception {


	}

	@Test
	public void shouldRunWithNoProblems() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("F1_211.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);
			Network n = theDocument.getNetwork().get(0);
			assertNotNull(n.getStations());
			Station s = n.getStations().get(0);
			EpochOverlapCondition condition = new EpochOverlapCondition(true,
					"Channel:Epoch cannot be partly concurrent with any other Channel:Epoch encompassed in parent Station:Epoch.");
			Message result = condition.evaluate(s);
			System.out.println(result.getDescription());
			Assert.assertTrue(result instanceof edu.iris.dmc.station.rules.Error);
		}

	}
}

package edu.iris.dmc.station.conditions;

import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Response;
import edu.iris.dmc.fdsn.station.model.ResponseStage;
import edu.iris.dmc.fdsn.station.model.Sensitivity;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.restrictions.Restriction;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.Result;
import edu.iris.dmc.station.rules.Util;

public class StageGainProductCondition extends ChannelRestrictedCondition {

	public StageGainProductCondition(boolean required, String description, Restriction... restrictions) {
		super(required, description, restrictions);
	}

	@Override
	public Message evaluate(Network network) {
		throw new IllegalArgumentException("method not supported!");
	}

	@Override
	public Message evaluate(Station station) {
		throw new IllegalArgumentException("method not supported!");
	}

	@Override
	public Message evaluate(Channel channel) {
		if (channel == null) {
			return Result.success();
		}
		return this.evaluate(channel, channel.getResponse());
	}

	@Override
	public Message evaluate(Channel channel,Response response) {
		if (isRestricted(channel)) {
			return Result.success();
		}

		if (this.required) {
			if (response == null) {
				return Result.error("expected response but was null");
			}
		}
		if (response.getInstrumentSensitivity() != null) {

			Sensitivity sensitivity = response.getInstrumentSensitivity();
			sensitivity.getValue();
			Double frequency = sensitivity.getFrequency();

			double product = 1;
			if (response.getStage() != null && !response.getStage().isEmpty()) {
				for (ResponseStage stage : response.getStage()) {
					if (stage.getStageGain() != null && stage.getStageGain().getFrequency()==frequency) {
						product = product * stage.getStageGain().getValue();
					} else {
						return Result.success();
					}
				}
				
				if(!Util.equal(product, response.getInstrumentSensitivity().getValue())){
					return Result.error("Product of stage gains " + product + " must equal total gain "
							+ response.getInstrumentSensitivity().getValue());
				}
			}
		}

		return Result.success();
	}

}

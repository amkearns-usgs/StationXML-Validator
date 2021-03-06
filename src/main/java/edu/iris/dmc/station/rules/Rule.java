package edu.iris.dmc.station.rules;

import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Response;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.actions.Action;
import edu.iris.dmc.station.conditions.Condition;

public class Rule {

	private int id;
	private Condition condition;
	// private Action action;

	public Rule(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getDescription() {
		return this.condition.getDescription();
	}

	public void execute(Network network, RuleContext context, Action action) {
		Message message = condition.evaluate(network);
		if (message instanceof Success) {
			return;
		}

		update(context,action, message, network, null, null);
	}

	private void update(RuleContext context, Action action, Message message, Network network, Station station,
			Channel channel) {
		message.setRule(this);
		message.setNetwork(network);
		message.setStation(station);
		message.setChannel(channel);
		action.update(context, message);
	}

	public void execute(Network network, Station station, RuleContext context, Action action) {
		Message message = condition.evaluate(station);
		if (message instanceof Success) {
			return;
		}
		update(context,action, message, network, station, null);
	}

	public void execute(Network network, Station station, Channel channel, RuleContext context, Action action) {
		Message message = condition.evaluate(channel);
		if (message instanceof Success) {
			return;
		}
		update(context,action, message, network, station, channel);
	}

	public void execute(Network network, Station station, Channel channel, Response response, RuleContext context,
			Action action) {
		Message message = condition.evaluate(channel, response);
		if (message instanceof Success) {
			return;
		}
		update(context,action, message, network, station, channel);
	}

	public Condition getConditionExpression() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		return "Rule{" + "conditionExpression=" + condition + "'}'";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rule other = (Rule) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

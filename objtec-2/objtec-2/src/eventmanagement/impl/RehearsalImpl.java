package eventmanagement.impl;

import eventmanagement.Rehearsal;


public class RehearsalImpl extends EventImpl implements Rehearsal {
	
	public RehearsalImpl(String name) {
		super(name);
	}
	@Override
	public String getShortTypeDescriptor() {
		return "Rehearsal";
	}
}

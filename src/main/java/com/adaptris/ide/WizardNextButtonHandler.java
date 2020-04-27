package com.adaptris.ide;

import com.adaptris.ide.node.ExternalConnection;

@FunctionalInterface
public interface WizardNextButtonHandler
{
	void onNext(ExternalConnection connection);
}

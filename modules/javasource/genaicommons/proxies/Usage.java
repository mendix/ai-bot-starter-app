// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package genaicommons.proxies;

/**
 * This entity represents usage statistics of a call to an LLM. It refers to a complete LLM interaction; in case there are several iterations (e.g. recursive procesisng of function calls), everything should be aggregated into one Usage record.
 * 
 * Following the principles of GenAI Commons it must be stored based on the response for every successful call to a system of an LLM provider. This is only applicable for text & files operations and embeddings operations. It is the responsibility of connector developers implementing the GenAI principles in their GenAI operations to include the right microflows to ensure storage of Usage details after successful calls.
 * 
 * The data stored in this entity is to be used later on for monitoring purposes.
 * 
 * See ScE_Usage_Cleanup and Usage_CleanUpAterDays for more information about automatic cleanup.
 */
public class Usage implements com.mendix.systemwideinterfaces.core.IEntityProxy
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject usageMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "GenAICommons.Usage";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		InputTokens("InputTokens"),
		OutputTokens("OutputTokens"),
		TotalTokens("TotalTokens"),
		DurationMilliseconds("DurationMilliseconds"),
		DeploymentIdentifier("DeploymentIdentifier");

		private final java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@java.lang.Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public Usage(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, entityName));
	}

	protected Usage(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject usageMendixObject)
	{
		if (usageMendixObject == null) {
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		}
		if (!com.mendix.core.Core.isSubClassOf(entityName, usageMendixObject.getType())) {
			throw new java.lang.IllegalArgumentException(String.format("The given object is not a %s", entityName));
		}	

		this.usageMendixObject = usageMendixObject;
		this.context = context;
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 * @param context The context to be used
	 * @param mendixObject The Mendix object for the new instance
	 * @return a new instance of this proxy class
	 */
	public static genaicommons.proxies.Usage initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new genaicommons.proxies.Usage(context, mendixObject);
	}

	public static genaicommons.proxies.Usage load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return genaicommons.proxies.Usage.initialize(context, mendixObject);
	}

	public static java.util.List<genaicommons.proxies.Usage> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		return com.mendix.core.Core.createXPathQuery(String.format("//%1$s%2$s", entityName, xpathConstraint))
			.execute(context)
			.stream()
			.map(obj -> genaicommons.proxies.Usage.initialize(context, obj))
			.collect(java.util.stream.Collectors.toList());
	}

	/**
	 * @return value of InputTokens
	 */
	public final java.lang.Integer getInputTokens()
	{
		return getInputTokens(getContext());
	}

	/**
	 * @param context
	 * @return value of InputTokens
	 */
	public final java.lang.Integer getInputTokens(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.InputTokens.toString());
	}

	/**
	 * Set value of InputTokens
	 * @param inputtokens
	 */
	public final void setInputTokens(java.lang.Integer inputtokens)
	{
		setInputTokens(getContext(), inputtokens);
	}

	/**
	 * Set value of InputTokens
	 * @param context
	 * @param inputtokens
	 */
	public final void setInputTokens(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer inputtokens)
	{
		getMendixObject().setValue(context, MemberNames.InputTokens.toString(), inputtokens);
	}

	/**
	 * @return value of OutputTokens
	 */
	public final java.lang.Integer getOutputTokens()
	{
		return getOutputTokens(getContext());
	}

	/**
	 * @param context
	 * @return value of OutputTokens
	 */
	public final java.lang.Integer getOutputTokens(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.OutputTokens.toString());
	}

	/**
	 * Set value of OutputTokens
	 * @param outputtokens
	 */
	public final void setOutputTokens(java.lang.Integer outputtokens)
	{
		setOutputTokens(getContext(), outputtokens);
	}

	/**
	 * Set value of OutputTokens
	 * @param context
	 * @param outputtokens
	 */
	public final void setOutputTokens(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer outputtokens)
	{
		getMendixObject().setValue(context, MemberNames.OutputTokens.toString(), outputtokens);
	}

	/**
	 * @return value of TotalTokens
	 */
	public final java.lang.Integer getTotalTokens()
	{
		return getTotalTokens(getContext());
	}

	/**
	 * @param context
	 * @return value of TotalTokens
	 */
	public final java.lang.Integer getTotalTokens(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.TotalTokens.toString());
	}

	/**
	 * Set value of TotalTokens
	 * @param totaltokens
	 */
	public final void setTotalTokens(java.lang.Integer totaltokens)
	{
		setTotalTokens(getContext(), totaltokens);
	}

	/**
	 * Set value of TotalTokens
	 * @param context
	 * @param totaltokens
	 */
	public final void setTotalTokens(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer totaltokens)
	{
		getMendixObject().setValue(context, MemberNames.TotalTokens.toString(), totaltokens);
	}

	/**
	 * @return value of DurationMilliseconds
	 */
	public final java.lang.Integer getDurationMilliseconds()
	{
		return getDurationMilliseconds(getContext());
	}

	/**
	 * @param context
	 * @return value of DurationMilliseconds
	 */
	public final java.lang.Integer getDurationMilliseconds(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.DurationMilliseconds.toString());
	}

	/**
	 * Set value of DurationMilliseconds
	 * @param durationmilliseconds
	 */
	public final void setDurationMilliseconds(java.lang.Integer durationmilliseconds)
	{
		setDurationMilliseconds(getContext(), durationmilliseconds);
	}

	/**
	 * Set value of DurationMilliseconds
	 * @param context
	 * @param durationmilliseconds
	 */
	public final void setDurationMilliseconds(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer durationmilliseconds)
	{
		getMendixObject().setValue(context, MemberNames.DurationMilliseconds.toString(), durationmilliseconds);
	}

	/**
	 * @return value of DeploymentIdentifier
	 */
	public final java.lang.String getDeploymentIdentifier()
	{
		return getDeploymentIdentifier(getContext());
	}

	/**
	 * @param context
	 * @return value of DeploymentIdentifier
	 */
	public final java.lang.String getDeploymentIdentifier(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.DeploymentIdentifier.toString());
	}

	/**
	 * Set value of DeploymentIdentifier
	 * @param deploymentidentifier
	 */
	public final void setDeploymentIdentifier(java.lang.String deploymentidentifier)
	{
		setDeploymentIdentifier(getContext(), deploymentidentifier);
	}

	/**
	 * Set value of DeploymentIdentifier
	 * @param context
	 * @param deploymentidentifier
	 */
	public final void setDeploymentIdentifier(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String deploymentidentifier)
	{
		getMendixObject().setValue(context, MemberNames.DeploymentIdentifier.toString(), deploymentidentifier);
	}

	@java.lang.Override
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return usageMendixObject;
	}

	@java.lang.Override
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this) {
			return true;
		}
		if (obj != null && getClass().equals(obj.getClass()))
		{
			final genaicommons.proxies.Usage that = (genaicommons.proxies.Usage) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@java.lang.Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

  /**
   * Gives full name ("Module.Entity" name) of the type of the entity.
   *
   * @return the name
   */
	public static java.lang.String getType()
	{
		return entityName;
	}
}
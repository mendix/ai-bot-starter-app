// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package genaicommons.proxies;

/**
 * Represents a piece of information (InputText) and the corresponding embeddings vector retieved from an Embeddings API.
 */
public class Chunk implements com.mendix.systemwideinterfaces.core.IEntityProxy
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject chunkMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "GenAICommons.Chunk";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		InputText("InputText"),
		EmbeddingVector("EmbeddingVector"),
		_Index("_Index");

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

	public Chunk(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, entityName));
	}

	protected Chunk(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject chunkMendixObject)
	{
		if (chunkMendixObject == null) {
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		}
		if (!com.mendix.core.Core.isSubClassOf(entityName, chunkMendixObject.getType())) {
			throw new java.lang.IllegalArgumentException(String.format("The given object is not a %s", entityName));
		}	

		this.chunkMendixObject = chunkMendixObject;
		this.context = context;
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 * @param context The context to be used
	 * @param mendixObject The Mendix object for the new instance
	 * @return a new instance of this proxy class
	 */
	public static genaicommons.proxies.Chunk initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		if (com.mendix.core.Core.isSubClassOf("GenAICommons.KnowledgeBaseChunk", mendixObject.getType())) {
			return genaicommons.proxies.KnowledgeBaseChunk.initialize(context, mendixObject);
		}
		return new genaicommons.proxies.Chunk(context, mendixObject);
	}

	public static genaicommons.proxies.Chunk load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return genaicommons.proxies.Chunk.initialize(context, mendixObject);
	}

	/**
	 * @return value of InputText
	 */
	public final java.lang.String getInputText()
	{
		return getInputText(getContext());
	}

	/**
	 * @param context
	 * @return value of InputText
	 */
	public final java.lang.String getInputText(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.InputText.toString());
	}

	/**
	 * Set value of InputText
	 * @param inputtext
	 */
	public final void setInputText(java.lang.String inputtext)
	{
		setInputText(getContext(), inputtext);
	}

	/**
	 * Set value of InputText
	 * @param context
	 * @param inputtext
	 */
	public final void setInputText(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String inputtext)
	{
		getMendixObject().setValue(context, MemberNames.InputText.toString(), inputtext);
	}

	/**
	 * @return value of EmbeddingVector
	 */
	public final java.lang.String getEmbeddingVector()
	{
		return getEmbeddingVector(getContext());
	}

	/**
	 * @param context
	 * @return value of EmbeddingVector
	 */
	public final java.lang.String getEmbeddingVector(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.EmbeddingVector.toString());
	}

	/**
	 * Set value of EmbeddingVector
	 * @param embeddingvector
	 */
	public final void setEmbeddingVector(java.lang.String embeddingvector)
	{
		setEmbeddingVector(getContext(), embeddingvector);
	}

	/**
	 * Set value of EmbeddingVector
	 * @param context
	 * @param embeddingvector
	 */
	public final void setEmbeddingVector(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String embeddingvector)
	{
		getMendixObject().setValue(context, MemberNames.EmbeddingVector.toString(), embeddingvector);
	}

	/**
	 * @return value of _Index
	 */
	public final java.lang.Integer get_Index()
	{
		return get_Index(getContext());
	}

	/**
	 * @param context
	 * @return value of _Index
	 */
	public final java.lang.Integer get_Index(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames._Index.toString());
	}

	/**
	 * Set value of _Index
	 * @param _index
	 */
	public final void set_Index(java.lang.Integer _index)
	{
		set_Index(getContext(), _index);
	}

	/**
	 * Set value of _Index
	 * @param context
	 * @param _index
	 */
	public final void set_Index(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer _index)
	{
		getMendixObject().setValue(context, MemberNames._Index.toString(), _index);
	}

	@java.lang.Override
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return chunkMendixObject;
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
			final genaicommons.proxies.Chunk that = (genaicommons.proxies.Chunk) obj;
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
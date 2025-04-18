// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mxgenaiconnector.actions;

import java.util.ArrayList;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import genaicommons.proxies.KnowledgeBaseChunk;
import mxgenaiconnector.impl.ChunkUtils;
import mxgenaiconnector.impl.MxLogger;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.UserAction;

/**
 * Use this operation to delete existing chunks in a collection based on the MxObjectID. 
 * MxObjectList is the list of (original) Mendix objects that the chunks in the knowledge base represent. Only chunks related to these Mendix objects are to be deleted.
 * 
 * The Connection entity passed must be of type MxKnowledgeBaseConnection and must contain the CollectionName string attribute filled and a MxCloudKnowledgeBase associated with the connection details to the knowledge base. By providing the CollectionName on the Connection, you determine the collection from which the chunks should be deleted.
 * Use MxKnowledgeBaseConnection_Create to create it.
 * 
 * Once deleted, chunks are no longer available for read operations in the KB after 60-120 seconds due to asynchronous data synchronization for better scalability.
 */
public class KnowledgeBaseChunkList_Delete_ByMxObjectList extends UserAction<java.lang.Boolean>
{
	/** @deprecated use Connection.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __Connection;
	private final genaicommons.proxies.Connection Connection;
	private final java.util.List<IMendixObject> MxObjectList;

	public KnowledgeBaseChunkList_Delete_ByMxObjectList(
		IContext context,
		IMendixObject _connection,
		java.util.List<IMendixObject> _mxObjectList
	)
	{
		super(context);
		this.__Connection = _connection;
		this.Connection = _connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), _connection);
		this.MxObjectList = _mxObjectList;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		try {
			if (MxObjectList.isEmpty()) {
				LOGGER.warn("Empty list was passed, nothing was deleted");
			}
			java.util.List<KnowledgeBaseChunk> chunkList = new ArrayList<>();
			MxObjectList.forEach(o -> ChunkUtils.addChunkWithMxObjectID(getContext(), o, chunkList));
			return mxgenaiconnector.proxies.microflows.Microflows.knowledgeBaseChunkList_Delete_FromKnowledgeBase(getContext(), chunkList, Connection);
		} catch (Error e) {
			LOGGER.error(e, "Delete list was not successful.");
			return false;
		}
		
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "KnowledgeBaseChunkList_Delete_ByMxObjectList";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(KnowledgeBaseChunkList_Delete_ByMxObjectList.class);
	// END EXTRA CODE
}

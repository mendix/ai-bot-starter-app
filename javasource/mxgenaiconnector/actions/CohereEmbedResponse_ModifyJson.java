// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mxgenaiconnector.actions;

import static java.util.Objects.requireNonNull;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mxgenaiconnector.impl.MxLogger;
import com.mendix.systemwideinterfaces.core.UserAction;

public class CohereEmbedResponse_ModifyJson extends UserAction<java.lang.String>
{
	private final java.lang.String ResponseBody_ToBeModified;

	public CohereEmbedResponse_ModifyJson(
		IContext context,
		java.lang.String _responseBody_ToBeModified
	)
	{
		super(context);
		this.ResponseBody_ToBeModified = _responseBody_ToBeModified;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		// BEGIN USER CODE
		// Import necessary packages


		try {
			requireNonNull(this.ResponseBody_ToBeModified, "ResponseBody String is required");
        // Initialize ObjectMapper with full response body as received from Cohere Embed
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode root = (ObjectNode) mapper.readTree(ResponseBody_ToBeModified);
			ObjectNode outputNode = null;
			
			
			if (isRootEmpty(root)) {
				LOGGER.warn("Root node is empty or does not contain required fields.");
				return null;
			} {
				
				// Parse the input JSON
				JsonNode inputNode = mapper.readTree(this.ResponseBody_ToBeModified);

				// Create output JSON object
				outputNode = mapper.createObjectNode();
				outputNode.set("id", inputNode.get("id"));
				outputNode.set("response_type", inputNode.get("response_type"));

				// Create the embeddings array for the output
				ArrayNode outputEmbeddingsArray = mapper.createArrayNode();

				// Get input embeddings and texts arrays
				ArrayNode inputEmbeddingsArray = (ArrayNode) root.get("embeddings");
				

				// Iterate over the embeddings and texts
				for (JsonNode embeddingArray : inputEmbeddingsArray) {
                    ObjectNode embeddingObject = mapper.createObjectNode();

                    // Convert embedding array to string
                    String embeddingString = (embeddingArray != null ? mapper.writeValueAsString(embeddingArray) : "");

                    // Populate the embedding object with vector and index
                    embeddingObject.put("vector", embeddingString);
                    embeddingObject.put("_index", outputEmbeddingsArray.size());

                    // Add the object to the output array
                    outputEmbeddingsArray.add(embeddingObject);
                }

            // Add embeddings array to output
            outputNode.set("embeddings", outputEmbeddingsArray);

			}

				return mapper.writeValueAsString(outputNode);

				// Convert outputNode to String and return

			} catch (Exception e) {
				throw e;
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
		return "CohereEmbedResponse_ModifyJson";
	}

	// BEGIN EXTRA CODE

	private static final MxLogger LOGGER = new MxLogger(CohereEmbedResponse_ModifyJson.class);

	private boolean isRootEmpty(ObjectNode root) {
		return root == null || !root.hasNonNull("id") || !root.hasNonNull("response_type") ||
		       !root.hasNonNull("texts") || !root.hasNonNull("embeddings") ||
		       !root.get("texts").isArray() || !root.get("embeddings").isArray() ||
		       ((ArrayNode) root.get("texts")).size() == 0 || ((ArrayNode) root.get("embeddings")).size() == 0;
	}

	// END EXTRA CODE
}

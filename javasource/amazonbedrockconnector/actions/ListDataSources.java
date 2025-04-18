// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package amazonbedrockconnector.actions;

import static java.util.Objects.requireNonNull;
import java.util.Date;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import amazonbedrockconnector.impl.AmazonBedrockClient;
import amazonbedrockconnector.impl.MxDataSource;
import amazonbedrockconnector.impl.MxLogger;
import amazonbedrockconnector.proxies.DataSourceSummary;
import amazonbedrockconnector.proxies.ListDataSourcesResponse;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.UserAction;

public class ListDataSources extends UserAction<IMendixObject>
{
	/** @deprecated use Credentials.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __Credentials;
	private final awsauthentication.proxies.Credentials Credentials;
	private final awsauthentication.proxies.ENUM_Region Region;
	/** @deprecated use ListDataSourcesRequest.getMendixObject() instead. */
	@java.lang.Deprecated(forRemoval = true)
	private final IMendixObject __ListDataSourcesRequest;
	private final amazonbedrockconnector.proxies.ListDataSourcesRequest ListDataSourcesRequest;

	public ListDataSources(
		IContext context,
		IMendixObject _credentials,
		java.lang.String _region,
		IMendixObject _listDataSourcesRequest
	)
	{
		super(context);
		this.__Credentials = _credentials;
		this.Credentials = _credentials == null ? null : awsauthentication.proxies.Credentials.initialize(getContext(), _credentials);
		this.Region = _region == null ? null : awsauthentication.proxies.ENUM_Region.valueOf(_region);
		this.__ListDataSourcesRequest = _listDataSourcesRequest;
		this.ListDataSourcesRequest = _listDataSourcesRequest == null ? null : amazonbedrockconnector.proxies.ListDataSourcesRequest.initialize(getContext(), _listDataSourcesRequest);
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		// BEGIN USER CODE
		try {
			// Validating JA input parameters
			requireNonNull(Credentials, "AWS Credentials are required");
			requireNonNull(ListDataSourcesRequest, "ListDataSourcesRequest is required");
			requireNonNull(Region, "AWS Region is required");
			
			validateRequest();
						
			BedrockAgentClient client = AmazonBedrockClient.getBedrockAgentClient(Credentials, Region, ListDataSourcesRequest);
			
			software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesRequest awsRequest = createAwsRequest();
			LOGGER.info("AWS request: " + awsRequest);
			
			software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesResponse awsResponse = client.listDataSources(awsRequest);
			LOGGER.info("AWS response: " + awsResponse);
			
			return getMxResponse(awsResponse).getMendixObject();
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
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
		return "ListDataSources";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(ListDataSources.class);
	
	private void validateRequest() throws CoreException {
		
		if (this.ListDataSourcesRequest.getKnowledgeBaseId() == null || this.ListDataSourcesRequest.getKnowledgeBaseId().isBlank()) {
			throw new IllegalArgumentException("KnowledgeBaseId is required.");
		}
	}
	
	private software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesRequest createAwsRequest() throws CoreException{
		
		software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesRequest.Builder awsRequestBuilder = software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesRequest.builder();
		awsRequestBuilder.knowledgeBaseId(this.ListDataSourcesRequest.getKnowledgeBaseId());
		
		if (this.ListDataSourcesRequest.getMaxResults()!=null) {
			awsRequestBuilder.maxResults(this.ListDataSourcesRequest.getMaxResults());
		}
		
		if (this.ListDataSourcesRequest.getNextToken() != null && !this.ListDataSourcesRequest.getNextToken().isBlank()) {
			awsRequestBuilder.nextToken(this.ListDataSourcesRequest.getNextToken());
		}
		return awsRequestBuilder.build();
		
	}
	
	private ListDataSourcesResponse getMxResponse(software.amazon.awssdk.services.bedrockagent.model.ListDataSourcesResponse awsResponse) {
		
		ListDataSourcesResponse mxResponse = new ListDataSourcesResponse(getContext());
		
		mxResponse.setNextToken(awsResponse.nextToken());
		awsResponse.dataSourceSummaries().forEach(awsDataSourceSummary -> createMxDataSourceSummary(awsDataSourceSummary,mxResponse));
		
		return mxResponse;
	}
	
	void createMxDataSourceSummary(software.amazon.awssdk.services.bedrockagent.model.DataSourceSummary awsDataSourceSummary, ListDataSourcesResponse mxResponse) {
		
		DataSourceSummary mxDataSourceSummary = new DataSourceSummary(getContext());
		mxDataSourceSummary.setDataSourceId(awsDataSourceSummary.dataSourceId());
		mxDataSourceSummary.setDescription(awsDataSourceSummary.description());
		mxDataSourceSummary.setKnowledgeBaseId(awsDataSourceSummary.knowledgeBaseId());
		mxDataSourceSummary.setName(awsDataSourceSummary.name());
		mxDataSourceSummary.setStatus(MxDataSource.getMxDataSourceStatus(awsDataSourceSummary.status()));
		mxDataSourceSummary.setUpdatedAt(Date.from(awsDataSourceSummary.updatedAt()));
		mxDataSourceSummary.setDataSourceSummary_ListDataSourcesResponse(mxResponse);
	}
	// END EXTRA CODE
}

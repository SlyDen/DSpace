/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.sherpa;

<<<<<<< HEAD
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
=======
import org.apache.http.HttpStatus;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
>>>>>>> 2eaaf84810b3a3de0907e3e37ab695b053cc381f
import org.dspace.core.ConfigurationManager;

public class SHERPAService
{
<<<<<<< HEAD
=======
    private CloseableHttpClient client = null;

    private int maxNumberOfTries;
    private long sleepBetweenTimeouts;
    private int timeout;

    /** log4j category */
    private static final Logger log = Logger.getLogger(SHERPAService.class);

    public SHERPAService() {
        HttpClientBuilder custom = HttpClients.custom();
        // httpclient 4.3+ doesn't appear to have any sensible defaults any more. Setting conservative defaults as not to hammer the SHERPA service too much.
        client=custom.disableAutomaticRetries().setMaxConnTotal(5).setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(timeout).build()).build();

    }


>>>>>>> 2eaaf84810b3a3de0907e3e37ab695b053cc381f
    public SHERPAResponse searchByJournalISSN(String query)
    {
        String endpoint = ConfigurationManager.getProperty("sherpa.romeo.url");
        String apiKey = ConfigurationManager.getProperty("sherpa.romeo.apikey");
<<<<<<< HEAD
        
        GetMethod method = null;
        try
        {
            HttpClient client = new HttpClient();
            method = new GetMethod(endpoint);

            NameValuePair id = new NameValuePair("issn", query);
            NameValuePair versions = new NameValuePair("versions", "all");
            NameValuePair[] params = null;
            if (StringUtils.isNotBlank(apiKey))
            {
                NameValuePair ak = new NameValuePair("ak", apiKey);
                params = new NameValuePair[] { id, versions, ak };
            }
            else
            {
                params = new NameValuePair[] { id, versions };
            }
            method.setQueryString(params);
            // Execute the method.
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK)
            {
                return new SHERPAResponse("SHERPA/RoMEO return not OK status: "
                        + statusCode);
            }

            return new SHERPAResponse(method.getResponseBodyAsStream());
        }
        catch (Exception e)
        {
            return new SHERPAResponse(
                    "Error processing the SHERPA/RoMEO answer");
        }
        finally
        {
            if (method != null)
            {
                method.releaseConnection();
            }
        }
=======

        HttpGet method = null;
        SHERPAResponse sherpaResponse = null;
        int numberOfTries = 0;

        while(numberOfTries<maxNumberOfTries && sherpaResponse==null) {
            numberOfTries++;

            try {
                Thread.sleep(sleepBetweenTimeouts * (numberOfTries-1));

                URIBuilder uriBuilder = new URIBuilder(endpoint);
                uriBuilder.addParameter("issn", query);
                uriBuilder.addParameter("versions", "all");
                if (StringUtils.isNotBlank(apiKey))
                    uriBuilder.addParameter("ak", apiKey);

                method = new HttpGet(uriBuilder.build());

                // Execute the method.

                HttpResponse response = client.execute(method);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    sherpaResponse = new SHERPAResponse("SHERPA/RoMEO return not OK status: "
                            + statusCode);
                }

                HttpEntity responseBody = response.getEntity();

                if (null != responseBody)
                    sherpaResponse = new SHERPAResponse(responseBody.getContent());
                else
                    sherpaResponse = new SHERPAResponse("SHERPA/RoMEO returned no response");
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }

        if(sherpaResponse==null){
            sherpaResponse = new SHERPAResponse(
                    "Error processing the SHERPA/RoMEO answer");
        }

        return sherpaResponse;
    }

    public void setMaxNumberOfTries(int maxNumberOfTries) {
        this.maxNumberOfTries = maxNumberOfTries;
    }

    public void setSleepBetweenTimeouts(long sleepBetweenTimeouts) {
        this.sleepBetweenTimeouts = sleepBetweenTimeouts;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
>>>>>>> 2eaaf84810b3a3de0907e3e37ab695b053cc381f
    }
}

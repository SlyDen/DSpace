/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.sherpa.submit;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import org.dspace.content.DCValue;
=======
import org.dspace.content.Metadatum;
>>>>>>> 2eaaf84810b3a3de0907e3e37ab695b053cc381f
import org.dspace.content.Item;
import org.dspace.core.Context;

public class MetadataAuthorityISSNExtractor implements ISSNItemExtractor
{
    private List<String> metadataList;

    public void setMetadataList(List<String> metadataList)
    {
        this.metadataList = metadataList;
    }

    @Override
    public List<String> getISSNs(Context context, Item item)
    {
        List<String> values = new ArrayList<String>();
        for (String metadata : metadataList)
        {
<<<<<<< HEAD
            DCValue[] dcvalues = item.getMetadata(metadata);
            for (DCValue dcvalue : dcvalues)
=======
            Metadatum[] dcvalues = item.getMetadataByMetadataString(metadata);
            for (Metadatum dcvalue : dcvalues)
>>>>>>> 2eaaf84810b3a3de0907e3e37ab695b053cc381f
            {
                values.add(dcvalue.authority);
            }
        }
        return values;
    }
}

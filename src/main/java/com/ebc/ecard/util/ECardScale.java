package com.ebc.ecard.util;

/**
 Marvin Project <2007-2013>
 http://www.marvinproject.org

 License information:
 http://marvinproject.sourceforge.net/en/license.html

 Discussion group:
 https://groups.google.com/forum/#!forum/marvin-project
 */

import marvin.gui.MarvinAttributesPanel;
import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.plugin.MarvinAbstractImagePlugin;
import marvin.util.MarvinAttributes;
import org.marvinproject.image.transform.scale.Scale;

/**
 * Simple and fast scale based on nearest neighbors
 * @author Gabriel Ambr�sio Archanjo
 */
public class ECardScale extends Scale {

    private MarvinAttributesPanel	attributesPanel;
    private int 					width;
    private int 					height;
    private int 					newWidth;
    private int 					newHeight;

    private MarvinAttributes 	attributes;

    public void process(
        MarvinImage a_imageIn,
        MarvinImage a_imageOut,
        MarvinAttributes a_attributesOut,
        MarvinImageMask a_mask,
        boolean previewMode
    ) {

        if(!previewMode){
            this.attributes = getAttributes();
            width = a_imageIn.getWidth();
            height = a_imageIn.getHeight();
            newWidth = (Integer)attributes.get("newWidth");
            newHeight = (Integer)attributes.get("newHeight");

            if(a_imageOut.getWidth() != newWidth || a_imageOut.getHeight() != newHeight){
                a_imageOut.setDimension(newWidth, newHeight);
            }

            int x_ratio = (int)((width<<16)/newWidth) ;
            int y_ratio = (int)((height<<16)/newHeight) ;
            int x2, y2 ;
            for (int i=0;i<newHeight;i++) {
                for (int j=0;j<newWidth;j++) {
                    x2 = ((j*x_ratio)>>16) ;
                    y2 = ((i*y_ratio)>>16) ;
                    a_imageOut.setIntColor(j,i, a_imageIn.getAlphaComponent(x2,y2), a_imageIn.getIntColor(x2,y2));
                }
            }
        }
    }
}

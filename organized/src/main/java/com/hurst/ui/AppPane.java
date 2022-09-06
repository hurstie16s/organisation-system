package com.hurst.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type App pane.
 */
public class AppPane extends StackPane {

    private static final Logger logger = LogManager.getLogger(AppPane.class);

    private final int width;
    private final int height;
    private final boolean autoScale = true;
    private double scalar = 1;

    /**
     * Instantiates a new App pane.
     *
     * @param width  the width
     * @param height the height
     */
    public AppPane(int width, int height) {
        super();
        this.width = width;
        this.height = height;

        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Sets scalar.
     *
     * @param Scalar the scalar
     */
    protected void setScalar(double Scalar) {
        this.scalar = Scalar;
    }

    @Override
    public void layoutChildren() {
        super.layoutChildren();

        if(!autoScale) return;

        var scaleFactorHeight = getHeight() / height;
        var scaleFactorWidth = getWidth() / width;

        setScalar(Math.min(scaleFactorWidth, scaleFactorHeight));

        Scale scale = new Scale(scalar, scalar);

        var parentWidth = getWidth();
        var parentHeight = getHeight();

        var paddingLeft = (parentWidth - (width * scalar)) / 2.0;
        var paddingTop = (parentHeight - (height * scalar)) / 2.0;

        Translate translate = new Translate(paddingLeft, paddingTop);
        scale.setPivotX(0);
        scale.setPivotY(0);
        getTransforms().setAll(translate, scale);
    }
}

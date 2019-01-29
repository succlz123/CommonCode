package org.succlz123.commoncode.opengles;

import org.succlz123.commoncode.R;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by succlz123 on 2018/2/21.
 */

public class ColorShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, null, null);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }
}
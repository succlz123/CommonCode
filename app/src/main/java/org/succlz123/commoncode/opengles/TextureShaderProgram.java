package org.succlz123.commoncode.opengles;

import org.succlz123.commoncode.R;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by succlz123 on 2018/2/21.
 */

public class TextureShaderProgram extends ShaderProgram {
    // uniforms
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    // attributes
    private final int aPositionLocation;
    private final int aTextureCoordinateLocation;

    public TextureShaderProgram(Context context, String vertexShader, String fragmentShader) {
        super(context, vertexShader, fragmentShader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinateLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        // Tell the texture uniform sampler to use this texture in the shader by
        // telling it to read from texture unit 0.
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesAttributeLocation() {
        return aTextureCoordinateLocation;
    }
}

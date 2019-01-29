package org.succlz123.commoncode.opengles;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by succlz123 on 2018/2/21.
 */

public class ShaderProgram {
    // uniforms
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // attributes
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // shader program
    protected final int program;

    protected ShaderProgram(Context context, String vertexShader, String fragmentShader) {
        program = ShaderHelper.buildProgram(vertexShader, fragmentShader);
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}

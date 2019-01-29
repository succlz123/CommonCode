package org.succlz123.commoncode.opengles;

import android.opengl.GLES20;

/**
 * Created by succlz123 on 2018/2/21.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = GLES20.glCreateShader(type);

        if (shaderObjectId == 0) {
            return 0;
        }

        // upload shader
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        LoggerHelper.i(TAG,
                "Result of compiling source: \n" + shaderCode + "\n\n" + GLES20.glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId);
            LoggerHelper.e(TAG, "Compilation of shader failed!");

            return 0;
        }

        return shaderObjectId;
    }

    public static int linkProgram(int vertextShaderId, int fragmentShaderId) {
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            LoggerHelper.w(TAG, "Could not create program!");
            return 0;
        }

        GLES20.glAttachShader(programObjectId, vertextShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        GLES20.glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        LoggerHelper.i(TAG, "Result of link program: \n" + GLES20.glGetProgramInfoLog(programObjectId));
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            LoggerHelper.e(TAG, "Link program failed");
            return 0;
        }

        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        LoggerHelper.i(TAG,
                "Result of validate program: " + validateStatus[0] + "\n" + GLES20.glGetProgramInfoLog(programObjectId));

        return validateStatus[0] != 0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        int program = linkProgram(vertexShader, fragmentShader);
        if (LoggerHelper.ON) {
            validateProgram(program);
        }

        return program;
    }
}

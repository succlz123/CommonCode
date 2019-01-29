package org.succlz123.commoncode;

import org.succlz123.commoncode.opengles.ColorShaderProgram;
import org.succlz123.commoncode.opengles.Mallet;
import org.succlz123.commoncode.opengles.Table;
import org.succlz123.commoncode.opengles.TextureHelper;
import org.succlz123.commoncode.opengles.TextureShaderProgram;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.CompletableFuture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGLSurfaceView;

    private static final String VERTEX_SHADER =
            "uniform mat4 u_Matrix;" +

                    "attribute vec4 a_Position;" +
                    "attribute vec4 a_Color;" +

                    "varying vec4 v_Color;" +

                    "attribute vec2 a_TextureCoordinates;" +
                    "varying vec2 v_TextureCoordinates;" +

                    "void main() {" +
                    "v_Color = a_Color;" +
                    "gl_Position = u_Matrix * a_Position;" +
                    "gl_PointSize = 10.0;" +

                    "v_TextureCoordinates = a_TextureCoordinates;" +

                    "}";

    private static final String FRAGMENT_SHADER =
            "precision mediump float;" +
                    "varying vec4 v_Color;" +

                    "uniform sampler2D u_TextureUnit;" +
                    "varying vec2 v_TextureCoordinates;" +

                    "void main() {" +
//                    "gl_FragColor = v_Color;" +
                    "gl_FragColor =  texture2D(u_TextureUnit, v_TextureCoordinates);" +
                    "}";

    private float[] projectMatrix = new float[16];
    private float[] modelMatrix = new float[16];

    private Table mTable;
    private Mallet mMallet;

    private TextureShaderProgram mTextureShaderProgram;
    private ColorShaderProgram mColorShaderProgram;

    private int texture;

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new HomeAdapter(LayoutInflater.from(this), DemoModel.values()));


//        mGLSurfaceView = findViewById(R.id.gl_surface);
//
//        mGLSurfaceView.setZOrderOnTop(true);
//        mGLSurfaceView.setEGLContextClientVersion(2);
//        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
//
//        mGLSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//                // Set the background color to black ( rgba ).
//                gl.glClearColor(0f, 0f, 0.0f, 0.0f);
//
//                mTable = new Table();
//                mMallet = new Mallet();
//
//                mTextureShaderProgram = new TextureShaderProgram(MainActivity.this, VERTEX_SHADER, FRAGMENT_SHADER);
////                mColorShaderProgram = new ColorShaderProgram();
//
//                texture = TextureHelper.loadTexture(MainActivity.this, R.drawable.liu);
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0, 0, width, height);
////                float aspectRatio = width > height ? (float) width / height : (float) height / width;
////                if (width > height) {
////                    Matrix.orthoM(projectMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
////                } else {
////                    Matrix.orthoM(projectMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
////                }
//                Matrix.perspectiveM(projectMatrix, 0, 45, (float) width / (float) height,
//                        1f, 10f);
//
//                Matrix.setIdentityM(modelMatrix, 0);
//
//                Matrix.translateM(modelMatrix, 0, 0, 0, -2.5f);
////                Matrix.rotateM(modelMatrix, 0, -60f, 1, 0, 0);
//
//                float[] temp = new float[16];
//                Matrix.multiplyMM(temp, 0, projectMatrix, 0, modelMatrix, 0);
//                System.arraycopy(temp, 0, projectMatrix, 0, temp.length);
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//
//                mTextureShaderProgram.useProgram();
//                mTextureShaderProgram.setUniforms(projectMatrix, texture);
//                mTable.bindData(mTextureShaderProgram);
//                mTable.draw();
//
//            }
//        });
//        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }



    private enum DemoModel {
        NAVIGATION("Navigation Demos", R.color.red_300),
        TRANSITIONS("Transition Demos", R.color.blue_grey_300),
        SHARED_ELEMENT_TRANSITIONS("Shared Element Demos", R.color.purple_300),
        CHILD_CONTROLLERS("Child Controllers", R.color.orange_300),
        VIEW_PAGER("ViewPager", R.color.green_300),
        TARGET_CONTROLLER("Target Controller", R.color.pink_300),
        MULTIPLE_CHILD_ROUTERS("Multiple Child Routers", R.color.deep_orange_300),
        MASTER_DETAIL("Master Detail", R.color.grey_300),
        DRAG_DISMISS("Drag Dismiss", R.color.lime_300),
        EXTERNAL_MODULES("Bonus Modules", R.color.teal_300);

        String title;
        int color;

        DemoModel(String title, int color) {
            this.title = title;
            this.color = color;
        }
    }

    class HomeAdapter extends RecyclerView.Adapter< ViewHolder> {

        private final LayoutInflater inflater;
        private final DemoModel[] items;

        public HomeAdapter(LayoutInflater inflater, DemoModel[] items) {
            this.inflater = inflater;
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.row_home, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position, items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        public void xx(){
            notifyDataSetChanged();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgDot;
        View rowRoot;
        private DemoModel model;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgDot = itemView.findViewById(R.id.img_dot);
            rowRoot = itemView.findViewById(R.id.row_root);
//            rowRoot.setOnClickListener(v -> onModelRowClick(model, position));
        }

        void bind(int position, DemoModel item) {
            model = item;
            tvTitle.setText(item.title);
//            imgDot.getDrawable().setColorFilter(ContextCompat.getColor(getActivity(), item.color), Mode.SRC_ATOP);
            this.position = position;

//                ViewCompat.setTransitionName(tvTitle, getResources().getString(R.string.transition_tag_title_indexed, position));
//                ViewCompat.setTransitionName(imgDot, getResources().getString(R.string.transition_tag_dot_indexed, position));
        }
    }
}

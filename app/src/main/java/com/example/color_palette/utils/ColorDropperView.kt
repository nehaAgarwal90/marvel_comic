package com.example.color_palette.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.almeros.android.multitouch.MoveGestureDetector
import com.example.color_palette.R
import com.example.color_palette.data.model.Point
import kotlin.math.abs
import kotlin.math.roundToInt

class ColorDropperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr), MoveGestureDetector.OnMoveGestureListener,
    GestureDetector.OnGestureListener {

    interface ColorDropperViewCallback {
        fun colorDropperViewOnColorChanged(color: Int)
    }

    private var dropperPoint: Point? = null

    private var backingBitmap: Bitmap? = null

    private var shader: BitmapShader? = null

    private val drawingMatrix: Matrix = Matrix()

    private val innerCircleBitmapPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var moveGestureDetector: MoveGestureDetector = MoveGestureDetector(context, this)
    private var gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, this)

    private var _currentColor: Int = Color.WHITE

    val currentColor: Int
        get() = _currentColor

    private val outerCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = OUTSIDE_CIRCLE_SIZE
        color = Color.WHITE
    }

    private val whiteCircleOuter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth =
            context.resources.getDimensionPixelSize(R.dimen.stroke_width_mask_pointer).toFloat()
        color = Color.WHITE
    }

    private var pixelPointRect = RectF()

    var callback: ColorDropperViewCallback? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            dropperPoint?.let { point ->
                if (backingBitmap != null) {
                    drawCircle(point.x, point.y, SIZE_OF_MAGNIFIER, innerCircleBitmapPaint)
                }
                //
                drawRect(pixelPointRect, whiteCircleOuter)
                drawCircle(
                    point.x,
                    point.y,
                    SIZE_OF_MAGNIFIER + OUTSIDE_CIRCLE_SIZE / 2,
                    outerCirclePaint
                )
                drawCircle(
                    point.x,
                    point.y,
                    SIZE_OF_MAGNIFIER + OUTSIDE_CIRCLE_SIZE,
                    whiteCircleOuter
                )
            }
            if (dropperPoint == null) setPoint(Point(measuredWidth / 2f, measuredHeight / 2f))
        }
    }

    fun setBackingBitmap(backingBitmap: Bitmap) {
        this.backingBitmap = backingBitmap
        shader = BitmapShader(backingBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        innerCircleBitmapPaint.shader = shader
        dropperPoint?.let { point ->
            updateZoomedBitmap(point)
        }
        invalidate()
    }

    private fun setPoint(point: Point) {
        this.dropperPoint = point

        pixelPointRect = RectF(
            point.x - PIXEL_POINT_SQUARE_SIZE, point.y - PIXEL_POINT_SQUARE_SIZE,
            point.x + PIXEL_POINT_SQUARE_SIZE, point.y + PIXEL_POINT_SQUARE_SIZE
        )

        updateZoomedBitmap(point)
    }

    private fun updateZoomedBitmap(imagePoint: Point) {
        val point = Point((imagePoint.x * getWidthRatio()), (imagePoint.y * getHeightRatio()))
        drawingMatrix.reset()
        drawingMatrix.postScale(MAGNIFIER_SCALE, MAGNIFIER_SCALE, point.x, point.y)
        innerCircleBitmapPaint.shader?.setLocalMatrix(drawingMatrix)
        backingBitmap?.let { bitmap ->
            val colorAtPoint = if (point.x.roundToInt() >= bitmap.width ||
                point.y.roundToInt() >= bitmap.height ||
                point.y.roundToInt() < 0 ||
                point.x.roundToInt() < 0
            ) {
                Color.WHITE
            } else {
                bitmap.getPixel(point.x.roundToInt(), point.y.roundToInt())
            }
            innerCircleBitmapPaint.color = colorAtPoint
            outerCirclePaint.color = colorAtPoint
            _currentColor = colorAtPoint
            callback?.colorDropperViewOnColorChanged(colorAtPoint)
        }
        invalidate()
    }

    private fun moveColorDropperPoint(translateX: Float, translateY: Float) {
        if (dropperPoint == null) {
            dropperPoint = Point(measuredWidth / 2f, measuredHeight / 2f)
        }
        val newDropperPoint = dropperPoint!! + Point(translateX, translateY)
        setPoint(newDropperPoint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    moveGestureDelta = 0f
                }
            }
            return moveGestureDetector.onTouchEvent(event)
                .or(gestureDetector.onTouchEvent(event))
        }
        return false
    }

    override fun onMoveBegin(detector: MoveGestureDetector?): Boolean {
        return true
    }

    private var moveGestureDelta = 0f

    override fun onMove(detector: MoveGestureDetector?): Boolean {
        detector?.let { gesture ->
            val moveThresholdReached = abs(moveGestureDelta) >= MOVE_THRESHOLD
            if (!moveThresholdReached) {
                moveGestureDelta += gesture.focusDelta.length()
            }
            if (moveThresholdReached && gesture.focusDelta.length() != 0f) {
                moveColorDropperPoint(gesture.focusDelta.x, gesture.focusDelta.y)
            }
            return true
        }
        return false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        backingBitmap = null
        shader = null
        dropperPoint = null
    }

    override fun onMoveEnd(detector: MoveGestureDetector?) {}

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(event: MotionEvent?): Boolean {
        event?.let {
            setPoint(Point(event.x, event.y))
            return true
        }
        return false
    }

    private fun getWidthRatio(): Float {
        backingBitmap?.let { bitmap ->
            return (bitmap.width.toFloat() / measuredWidth)
        }
        return 1f
    }

    private fun getHeightRatio(): Float {
        backingBitmap?.let { bitmap ->
            return (bitmap.height.toFloat() / measuredHeight)
        }
        return 1f
    }

    override fun onDown(e: MotionEvent?): Boolean = true

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean = false

    override fun onLongPress(e: MotionEvent?) {}

    companion object {
        private const val SIZE_OF_MAGNIFIER = 120f
        private const val PIXEL_POINT_SQUARE_SIZE = 10
        private const val MAGNIFIER_SCALE = 3f
        private const val OUTSIDE_CIRCLE_SIZE = 30f
        private const val MOVE_THRESHOLD = 20f

        private const val PROPERTY_X = "property_x"
        private const val PROPERTY_Y = "property_y"
    }
}
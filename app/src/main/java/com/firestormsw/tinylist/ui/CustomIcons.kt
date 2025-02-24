package com.firestormsw.tinylist.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Add: ImageVector
    get() {
        if (_Add != null) {
            return _Add!!
        }
        _Add = ImageVector.Builder(
            name = "Add",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(440f, 520f)
                horizontalLineTo(200f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(240f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(80f)
                horizontalLineTo(520f)
                verticalLineToRelative(240f)
                horizontalLineToRelative(-80f)
                close()
            }
        }.build()
        return _Add!!
    }

private var _Add: ImageVector? = null

public val Remove: ImageVector
    get() {
        if (_Remove != null) {
            return _Remove!!
        }
        _Remove = ImageVector.Builder(
            name = "Remove",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(200f, 520f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(80f)
                close()
            }
        }.build()
        return _Remove!!
    }

private var _Remove: ImageVector? = null

public val Arrow_back: ImageVector
    get() {
        if (_Arrow_back != null) {
            return _Arrow_back!!
        }
        _Arrow_back = ImageVector.Builder(
            name = "Arrow_back",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(313f, 520f)
                lineToRelative(224f, 224f)
                lineToRelative(-57f, 56f)
                lineToRelative(-320f, -320f)
                lineToRelative(320f, -320f)
                lineToRelative(57f, 56f)
                lineToRelative(-224f, 224f)
                horizontalLineToRelative(487f)
                verticalLineToRelative(80f)
                close()
            }
        }.build()
        return _Arrow_back!!
    }

private var _Arrow_back: ImageVector? = null

public val EllipsisVertical: ImageVector
    get() {
        if (_EllipsisVertical != null) {
            return _EllipsisVertical!!
        }
        _EllipsisVertical = ImageVector.Builder(
            name = "EllipsisVertical",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13f, 12f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 13f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 12f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 12f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13f, 5f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 6f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 5f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 5f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(13f, 19f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 20f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 19f)
                arcTo(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 19f)
                close()
            }
        }.build()
        return _EllipsisVertical!!
    }

private var _EllipsisVertical: ImageVector? = null

public val Star: ImageVector
    get() {
        if (_Star != null) {
            return _Star!!
        }
        _Star = ImageVector.Builder(
            name = "Star",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(6.97942f, 1.25171f)
                lineTo(6.9585f, 1.30199f)
                lineTo(5.58662f, 4.60039f)
                curveTo(5.5434f, 4.7043f, 5.4457f, 4.7752f, 5.3336f, 4.7842f)
                lineTo(1.7727f, 5.0697f)
                lineTo(1.71841f, 5.07405f)
                lineTo(1.38687f, 5.10063f)
                lineTo(1.08608f, 5.12475f)
                curveTo(0.8201f, 5.1461f, 0.7122f, 5.478f, 0.9149f, 5.6516f)
                lineTo(1.14406f, 5.84793f)
                lineTo(1.39666f, 6.06431f)
                lineTo(1.43802f, 6.09974f)
                lineTo(4.15105f, 8.42374f)
                curveTo(4.2365f, 8.4969f, 4.2738f, 8.6118f, 4.2477f, 8.7212f)
                lineTo(3.41882f, 12.196f)
                lineTo(3.40618f, 12.249f)
                lineTo(3.32901f, 12.5725f)
                lineTo(3.25899f, 12.866f)
                curveTo(3.1971f, 13.1256f, 3.4794f, 13.3308f, 3.7072f, 13.1917f)
                lineTo(3.9647f, 13.0344f)
                lineTo(4.24854f, 12.861f)
                lineTo(4.29502f, 12.8326f)
                lineTo(7.34365f, 10.9705f)
                curveTo(7.4397f, 10.9119f, 7.5604f, 10.9119f, 7.6564f, 10.9705f)
                lineTo(10.705f, 12.8326f)
                lineTo(10.7515f, 12.861f)
                lineTo(11.0354f, 13.0344f)
                lineTo(11.2929f, 13.1917f)
                curveTo(11.5206f, 13.3308f, 11.803f, 13.1256f, 11.7411f, 12.866f)
                lineTo(11.671f, 12.5725f)
                lineTo(11.5939f, 12.249f)
                lineTo(11.5812f, 12.196f)
                lineTo(10.7524f, 8.72118f)
                curveTo(10.7263f, 8.6118f, 10.7636f, 8.4969f, 10.849f, 8.4237f)
                lineTo(13.562f, 6.09974f)
                lineTo(13.6034f, 6.06431f)
                lineTo(13.856f, 5.84793f)
                lineTo(14.0852f, 5.65162f)
                curveTo(14.2878f, 5.478f, 14.18f, 5.1461f, 13.914f, 5.1247f)
                lineTo(13.6132f, 5.10063f)
                lineTo(13.2816f, 5.07405f)
                lineTo(13.2274f, 5.0697f)
                lineTo(9.66645f, 4.78422f)
                curveTo(9.5543f, 4.7752f, 9.4566f, 4.7043f, 9.4134f, 4.6004f)
                lineTo(8.04155f, 1.30199f)
                lineTo(8.02064f, 1.25171f)
                lineTo(7.89291f, 0.944609f)
                lineTo(7.77702f, 0.665992f)
                curveTo(7.6745f, 0.4196f, 7.3255f, 0.4196f, 7.223f, 0.666f)
                lineTo(7.10715f, 0.944609f)
                lineTo(6.97942f, 1.25171f)
                close()
                moveTo(7.50003f, 2.60397f)
                lineTo(6.50994f, 4.98442f)
                curveTo(6.3227f, 5.4345f, 5.8994f, 5.7421f, 5.4135f, 5.781f)
                lineTo(2.84361f, 5.98705f)
                lineTo(4.8016f, 7.66428f)
                curveTo(5.1718f, 7.9814f, 5.3335f, 8.479f, 5.2204f, 8.9532f)
                lineTo(4.62221f, 11.461f)
                lineTo(6.8224f, 10.1171f)
                curveTo(7.2384f, 9.863f, 7.7616f, 9.863f, 8.1777f, 10.1171f)
                lineTo(10.3778f, 11.461f)
                lineTo(9.77965f, 8.95321f)
                curveTo(9.6665f, 8.479f, 9.8282f, 7.9814f, 10.1984f, 7.6643f)
                lineTo(12.1564f, 5.98705f)
                lineTo(9.58654f, 5.78103f)
                curveTo(9.1006f, 5.7421f, 8.6773f, 5.4345f, 8.4901f, 4.9844f)
                lineTo(7.50003f, 2.60397f)
                close()
            }
        }.build()
        return _Star!!
    }

private var _Star: ImageVector? = null

public val StarFilled: ImageVector
    get() {
        if (_StarFilled != null) {
            return _StarFilled!!
        }
        _StarFilled = ImageVector.Builder(
            name = "StarFilled",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.22303f, 0.665992f)
                curveTo(7.3255f, 0.4196f, 7.6745f, 0.4196f, 7.777f, 0.666f)
                lineTo(9.41343f, 4.60039f)
                curveTo(9.4566f, 4.7043f, 9.5543f, 4.7752f, 9.6664f, 4.7842f)
                lineTo(13.914f, 5.12475f)
                curveTo(14.18f, 5.1461f, 14.2878f, 5.478f, 14.0852f, 5.6516f)
                lineTo(10.849f, 8.42374f)
                curveTo(10.7636f, 8.4969f, 10.7263f, 8.6118f, 10.7524f, 8.7212f)
                lineTo(11.7411f, 12.866f)
                curveTo(11.803f, 13.1256f, 11.5206f, 13.3308f, 11.2929f, 13.1917f)
                lineTo(7.6564f, 10.9705f)
                curveTo(7.5604f, 10.9119f, 7.4397f, 10.9119f, 7.3437f, 10.9705f)
                lineTo(3.70718f, 13.1917f)
                curveTo(3.4794f, 13.3308f, 3.1971f, 13.1256f, 3.259f, 12.866f)
                lineTo(4.24769f, 8.72118f)
                curveTo(4.2738f, 8.6118f, 4.2365f, 8.4969f, 4.151f, 8.4237f)
                lineTo(0.914889f, 5.65162f)
                curveTo(0.7122f, 5.478f, 0.8201f, 5.1461f, 1.0861f, 5.1247f)
                lineTo(5.3336f, 4.78422f)
                curveTo(5.4457f, 4.7752f, 5.5434f, 4.7043f, 5.5866f, 4.6004f)
                lineTo(7.22303f, 0.665992f)
                close()
            }
        }.build()
        return _StarFilled!!
    }

private var _StarFilled: ImageVector? = null

public val Heart_plus: ImageVector
    get() {
        if (_Heart_plus != null) {
            return _Heart_plus!!
        }
        _Heart_plus = ImageVector.Builder(
            name = "Heart_plus",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(440f, 840f)
                lineTo(313f, 726f)
                quadToRelative(-72f, -65f, -123.5f, -116f)
                reflectiveQuadToRelative(-85f, -96f)
                reflectiveQuadToRelative(-49f, -87f)
                reflectiveQuadTo(40f, 339f)
                quadToRelative(0f, -94f, 63f, -156.5f)
                reflectiveQuadTo(260f, 120f)
                quadToRelative(52f, 0f, 99f, 22f)
                reflectiveQuadToRelative(81f, 62f)
                quadToRelative(34f, -40f, 81f, -62f)
                reflectiveQuadToRelative(99f, -22f)
                quadToRelative(81f, 0f, 136f, 45.5f)
                reflectiveQuadTo(831f, 280f)
                horizontalLineToRelative(-85f)
                quadToRelative(-18f, -40f, -53f, -60f)
                reflectiveQuadToRelative(-73f, -20f)
                quadToRelative(-51f, 0f, -88f, 27.5f)
                reflectiveQuadTo(463f, 300f)
                horizontalLineToRelative(-46f)
                quadToRelative(-31f, -45f, -70.5f, -72.5f)
                reflectiveQuadTo(260f, 200f)
                quadToRelative(-57f, 0f, -98.5f, 39.5f)
                reflectiveQuadTo(120f, 339f)
                quadToRelative(0f, 33f, 14f, 67f)
                reflectiveQuadToRelative(50f, 78.5f)
                reflectiveQuadToRelative(98f, 104f)
                reflectiveQuadTo(440f, 732f)
                quadToRelative(26f, -23f, 61f, -53f)
                reflectiveQuadToRelative(56f, -50f)
                lineToRelative(9f, 9f)
                lineToRelative(19.5f, 19.5f)
                lineTo(605f, 677f)
                lineToRelative(9f, 9f)
                quadToRelative(-22f, 20f, -56f, 49.5f)
                reflectiveQuadTo(498f, 788f)
                close()
                moveToRelative(280f, -160f)
                verticalLineToRelative(-120f)
                horizontalLineTo(600f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(-120f)
                horizontalLineToRelative(80f)
                verticalLineToRelative(120f)
                horizontalLineToRelative(120f)
                verticalLineToRelative(80f)
                horizontalLineTo(800f)
                verticalLineToRelative(120f)
                close()
            }
        }.build()
        return _Heart_plus!!
    }

private var _Heart_plus: ImageVector? = null

public val Heart_minus: ImageVector
    get() {
        if (_Heart_minus != null) {
            return _Heart_minus!!
        }
        _Heart_minus = ImageVector.Builder(
            name = "Heart_minus",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(440f, 840f)
                lineTo(313f, 726f)
                quadToRelative(-72f, -65f, -123.5f, -116f)
                reflectiveQuadToRelative(-85f, -96f)
                reflectiveQuadToRelative(-49f, -87f)
                reflectiveQuadTo(40f, 339f)
                quadToRelative(0f, -94f, 63f, -156.5f)
                reflectiveQuadTo(260f, 120f)
                quadToRelative(52f, 0f, 99f, 22f)
                reflectiveQuadToRelative(81f, 62f)
                quadToRelative(34f, -40f, 81f, -62f)
                reflectiveQuadToRelative(99f, -22f)
                quadToRelative(84f, 0f, 153f, 59f)
                reflectiveQuadToRelative(69f, 160f)
                quadToRelative(0f, 14f, -2f, 29.5f)
                reflectiveQuadToRelative(-6f, 31.5f)
                horizontalLineToRelative(-85f)
                quadToRelative(5f, -18f, 8f, -34f)
                reflectiveQuadToRelative(3f, -30f)
                quadToRelative(0f, -75f, -50f, -105.5f)
                reflectiveQuadTo(620f, 200f)
                quadToRelative(-51f, 0f, -88f, 27.5f)
                reflectiveQuadTo(463f, 300f)
                horizontalLineToRelative(-46f)
                quadToRelative(-31f, -45f, -70.5f, -72.5f)
                reflectiveQuadTo(260f, 200f)
                quadToRelative(-57f, 0f, -98.5f, 39.5f)
                reflectiveQuadTo(120f, 339f)
                quadToRelative(0f, 33f, 14f, 67f)
                reflectiveQuadToRelative(50f, 78.5f)
                reflectiveQuadToRelative(98f, 104f)
                reflectiveQuadTo(440f, 732f)
                quadToRelative(26f, -23f, 61f, -53f)
                reflectiveQuadToRelative(56f, -50f)
                lineToRelative(9f, 9f)
                lineToRelative(19.5f, 19.5f)
                lineTo(605f, 677f)
                lineToRelative(9f, 9f)
                quadToRelative(-22f, 20f, -56f, 49.5f)
                reflectiveQuadTo(498f, 788f)
                close()
                moveToRelative(160f, -280f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(80f)
                close()
            }
        }.build()
        return _Heart_minus!!
    }

private var _Heart_minus: ImageVector? = null

package com.example.bt1_tuan3

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bt1_tuan3.ui.theme.BT1_TUAN3Theme
import kotlinx.coroutines.channels.ticker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT1_TUAN3Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf("home") }
    var selectedComponent by remember { mutableStateOf("") }

    when (currentScreen) {
        "home" -> JetpackComposeScreen { currentScreen = "list" }
        "list" -> JetpackComposeList(
            onBackClick = { currentScreen = "home" },
            onItemClick = { component ->
                selectedComponent = component
                currentScreen = "detail"
            }
        )
        "detail" -> DetailScreen(componentName = selectedComponent) { currentScreen = "list" }
    }
}

// ✅ Màn hình chính
@Composable
fun JetpackComposeScreen(onReadyClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Jetpack Compose",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(200.dp))

        // ✅ Khi nhấn, gọi `onReadyClick()` để chuyển trang
        Button(
            onClick = onReadyClick,
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF007AFF))
        ) {
            Text(text = "I'm ready", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ✅ Màn hình danh sách UI Components
@Composable
fun JetpackComposeList(onBackClick: () -> Unit, onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Thêm padding để căn chỉnh chiều cao tốt hơn
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Sử dụng IconButton thay vì Button để tránh padding dư
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Tiêu đề căn giữa
            Text(
                text = "UI Components List",
                textAlign = TextAlign.Center,
                fontSize = 22.sp, // Giảm một chút để phù hợp với UI chuẩn
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.weight(1f) // Đẩy tiêu đề vào giữa
            )
        }

        Spacer(modifier = Modifier.height(12.dp)) // Giảm chiều cao spacer

        // ✅ Thêm thanh cuộn khi danh sách dài
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // Danh mục "Display"
            SectionTitle(title = "Display")
            ComponentItem(title = "Text", description = "Displays text", onClick = onItemClick)
            ComponentItem(title = "Image", description = "Displays an image", onClick = onItemClick)

            // Danh mục "Input"
            SectionTitle(title = "Input")
            ComponentItem(title = "TextField", description = "Input field for text", onClick = onItemClick)
            ComponentItem(title = "PasswordField", description = "Input field for passwords", onClick = onItemClick)

            // Danh mục "Layout"
            SectionTitle(title = "Layout")
            ComponentItem(title = "Column", description = "Arranges elements vertically", onClick = onItemClick)
            ComponentItem(title = "Row", description = "Arranges elements horizontally", onClick = onItemClick)

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


// ✅ Tiêu đề danh mục (VD: "Display", "Input", "Layout")
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

// ✅ Thành phần trong danh sách (có sự kiện khi nhấn)
@Composable
fun ComponentItem(title: String, description: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick(title) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0E7FF)) // Màu nền xanh nhạt
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

// ✅ Màn hình chi tiết khi chọn một thành phần
@Composable
fun DetailScreen(componentName: String, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Căn trên để giống giao diện iOS
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Thêm padding để căn chỉnh chiều cao tốt hơn
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }

            // Tiêu đề căn giữa
            Text(
                text = "Text Detail",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.weight(1f) // Đẩy tiêu đề vào giữa
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Nội dung văn bản với nhiều kiểu chữ
        Text(
            text = buildAnnotatedString {
                append("The ")

                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                    append("quick")
                }

                append(" ")

                withStyle(style = SpanStyle(color = Color(0xFF8B4513), fontWeight = FontWeight.Bold)) {
                    append("Brown")
                }

                append("\nfox j u m p s ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("over")
                }

                append("\nthe ")

                withStyle(style = SpanStyle(fontWeight =  FontWeight.Bold)) {
                    append("lazy")
                }

                append(" dog.")
            },
            fontSize = 22.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

package com.example.lab04

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val imageItems = listOf(
        "arms", "ears", "eyes", "eyebrows", "glasses",
        "hat", "mouth", "mustache", "nose", "shoes"
    )

    // Saveable 상태 관리
    var selectedStates by rememberSaveable {
        mutableStateOf(imageItems.associateWith { false }.toList())
    }

    // 리스트를 맵으로 변환해서 쓰기 편하게
    val selectedItems = remember(selectedStates) {
        selectedStates.toMap().toMutableMap()
    }

    val orientation = LocalConfiguration.current.orientation

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        // 세로 모드
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("202111251 김록연") //이름 출력
            ImageDisplaySection(selectedItems)
            Spacer(modifier = Modifier.height(16.dp))
            CheckBoxSection(imageItems, selectedItems) {
                selectedItems[it.first] = it.second
                selectedStates = selectedItems.toList()
            }
        }
    }  else { //가로모드
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 상단 텍스트
            Text(
                text = "202111251 김록연",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Row: 이미지 + 체크박스
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 이미지 영역
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    ImageDisplaySection(selectedItems)
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 체크박스 영역
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CheckBoxSection(imageItems, selectedItems) {
                        selectedItems[it.first] = it.second
                        selectedStates = selectedItems.toList()
                    }
                }
            }
        }
    }
}

@Composable
fun ImageDisplaySection(selectedItems: Map<String, Boolean>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        // body는 항상 표시
        Image(
            painter = painterResource(id = getImageId("body")),
            contentDescription = "body"
        )

        // 선택된 항목만 표시
        selectedItems.forEach { (name, isVisible) ->
            if (isVisible) {
                Image(
                    painter = painterResource(id = getImageId(name)),
                    contentDescription = name
                )
            }
        }
    }
}

@Composable
fun CheckBoxSection(
    items: List<String>,
    selectedItems: Map<String, Boolean>,
    onCheckedChange: (Pair<String, Boolean>) -> Unit
) {
    Column {
        items.chunked(2).forEach { rowItems ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        CheckBox(
                            label = item,
                            checked = selectedItems[item] == true,
                            onCheckedChange = { checked ->
                                onCheckedChange(item to checked)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun getImageId(name: String): Int {
    return LocalContext.current.resources
        .getIdentifier(name, "drawable", LocalContext.current.packageName)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

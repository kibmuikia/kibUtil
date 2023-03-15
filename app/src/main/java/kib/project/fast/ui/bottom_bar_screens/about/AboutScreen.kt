package kib.project.fast.ui.bottom_bar_screens.about

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kib.project.fast.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun AboutScreen(navHostController: NavHostController) {
    val viewModel: AboutScreenViewModel = getViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    AboutScreenContent(context = context, coroutineScope = coroutineScope, viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreenContent(
    context: Context,
    coroutineScope: CoroutineScope,
    viewModel: AboutScreenViewModel,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipPartiallyExpanded = false,
            confirmValueChange = { _: SheetValue -> true },
        ),
    )

    BottomSheetScaffold(
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .defaultMinSize(minHeight = 300.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.generic_sentence),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar("Closing Sheet")
                    }
                }) {
                    Text(text = "Hide Sheet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
//        sheetContainerColor =,
//        sheetContentColor =,
//        sheetTonalElevation =,
//        sheetDragHandle = {},
        sheetSwipeEnabled = true,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_about),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            )
        },
//        snackbarHost = {},
//        containerColor =,
//        contentColor =,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.generic_sentence),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(onClick = {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }) {
                Text(text = "Show Bottom-Sheet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
@Preview
private fun AboutScreenContentPreview() {
    AboutScreenContent(
        context = LocalContext.current,
        coroutineScope = rememberCoroutineScope(),
        viewModel = AboutScreenViewModel()
    )
}
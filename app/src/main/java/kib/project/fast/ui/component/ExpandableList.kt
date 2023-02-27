package kib.project.fast.ui.component

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kib.project.fast.R
import kib.project.fast.ui.component.models.ExpandableCardModel
import kib.project.fast.ui.component.viewmodels.ExpandableListViewModel
import kib.project.fast.utils.EXPANSION_TRANSITION_DURATION
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun ExpandableList(
    viewModel: ExpandableListViewModel
) {
    ExpandableListContent(viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandableListContent(
    viewModel: ExpandableListViewModel
) {
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    val expandedCardIds by viewModel.expandedCardIdsList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column {
        LazyColumn(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .wrapContentHeight()
        ) {
            items(count = cards.size) { index ->
                ExpandableCard(
                    card = cards[index],
                    onCardArrowClick = { viewModel.onCardArrowClicked(cards[index].id) },
                    expanded = expandedCardIds.contains(cards[index].id),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpandableListContentPreview() {
    ExpandableListContent(
        viewModel = ExpandableListViewModel()
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun ExpandableCard(
    card: ExpandableCardModel,
    expanded: Boolean = false,
    onCardArrowClick: () -> Unit = {},
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "bgColorTransition") {
        if (expanded) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "paddingTransition") {
        if (expanded) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "elevationTransition") {
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPANSION_TRANSITION_DURATION,
            easing = FastOutSlowInEasing
        )
    }, label = "cornersTransition") {
        if (expanded) 8.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPANSION_TRANSITION_DURATION)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }
    val context = LocalContext.current
    val contentColour = remember {
        Color.Black
    }

    Card(
        colors = CardDefaults.cardColors(
            contentColor = contentColour,
            containerColor = cardBgColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = card.title)
            }
            ExpandableContent(visible = expanded, expandableContent = card.expandableContent)
        }
    }
}

@Preview
@Composable
private fun ExpandableCardPreview() {
    ExpandableCard(
        card = ExpandableCardModel(
            id = 0,
            title = stringResource(id = R.string.placeholder_title),
            expandableContent = {}),
        onCardArrowClick = {},
        expanded = true,
    )
}

@Composable
private fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_expand_less_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}

@Preview
@Composable
private fun CardArrowPreview() {
    CardArrow(
        degrees = 0F,
        onClick = {}
    )
}

@Composable
private fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun CardTitlePreview() {
    CardTitle(title = stringResource(id = R.string.placeholder_title))
}

@Composable
private fun ExpandableContent(
    visible: Boolean = true,
    expandableContent: @Composable () -> Unit = {},
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSION_TRANSITION_DURATION)
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(24.dp))
            expandableContent()
        }
    }
}

@Preview
@Composable
private fun ExpandableContentPreview() {
    ExpandableContent(
        visible = true,
        expandableContent = {
            Text(
                text = stringResource(id = R.string.generic_sentence),
                textAlign = TextAlign.Center
            )
        }
    )
}
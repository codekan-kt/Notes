package com.codekan.notes.android.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codekan.notes.database.Notes
import com.codekan.notes.domain.entity.Note

/**
 * A composable function that displays a single note item.
 *
 * @param note The note to be displayed.
 * @param onClick The callback to be invoked when the note is clicked.
 * @param noteCount The number of notes in the list.
 * @param modifier Optional [Modifier] for styling.
 */
@Composable
fun NoteItem(note: Note, onClick: () -> Unit, noteCount : Int, modifier: Modifier = Modifier) {
    val color = when (noteCount.toLong() % 4) { // Colour codding on notes
        0L -> Color(0xFFFFF9C4) // Yellow
        1L -> Color(0xFFB3E5FC) // Blue
        2L -> Color(0xFFFFCCBC) // Red
        else -> Color(0xFFC8E6C9) // Green
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
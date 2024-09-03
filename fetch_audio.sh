#!/bin/bash

# Path to the desired WAV file
OUTPUT_DIR="src/main/resources/music"
OUTPUT_FILE="$OUTPUT_DIR/Caramelldansen.wav"

# URL of the YouTube video
VIDEO_URL="https://www.youtube.com/watch?v=6-8E4Nirh9s"

# Check if the WAV file exists
if [ ! -f "$OUTPUT_FILE" ]; then
    echo "Caramelldansen.wav not found. Downloading and converting..."

    # Ensure the output directory exists
    mkdir -p "$OUTPUT_DIR"

    # Use yt-dlp to download and convert the video to WAV format
    yt-dlp -x --audio-format wav -o "$OUTPUT_DIR/Caramelldansen.%(ext)s" "$VIDEO_URL"

    echo "Download and conversion complete: $OUTPUT_FILE"
else
    echo "Caramelldansen.wav already exists."
fi

from PIL import Image, ImageDraw, ImageFont
import os

suits = ["hearts", "diamonds", "clubs", "spades"]
numbers = ["ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"]

# Load a font. Adjust the path if necessary.
font_size = 10
try:
    font = ImageFont.truetype("arial.ttf", font_size)
except IOError:
    font = ImageFont.load_default()


def create_card_image(number, suit):
    # Create an image with a white background
    img = Image.new("RGBA", (64, 64), (255, 255, 255, 255))
    draw = ImageDraw.Draw(img)

    # Draw the border
    draw.rectangle([0, 0, 63, 63], outline="black")

    # Calculate text size and position
    text = f"{number} of {suit}"
    text_bbox = draw.textbbox((0, 0), text, font=font)
    text_width = text_bbox[2] - text_bbox[0]
    text_height = text_bbox[3] - text_bbox[1]

    # Draw text centered
    text_x = (64 - text_width) / 2
    text_y = (64 - text_height) / 2
    draw.text((text_x, text_y), text, font=font, fill="black")

    return img


output_dir = "cards"
os.makedirs(output_dir, exist_ok=True)

for suit in suits:
    for number in numbers:
        img = create_card_image(number, suit)
        img.save(os.path.join(output_dir, f"{number}_of_{suit}.png"))

print("Card images have been generated.")

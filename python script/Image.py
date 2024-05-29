from PIL import Image

def add_padding(image_path, output_path, target_size):
    image = Image.open(image_path)
    width, height = image.size
    new_image = Image.new("RGBA", (target_size, target_size), (0, 0, 0, 0))
    
    left = (target_size - width) // 2
    top = (target_size - height) // 2
    new_image.paste(image, (left, top))
    new_image.save(output_path)

# Paths to your images

for x in range(2, 152):
    num = x
    num = str(num).zfill(3)

    image_paths = [f"/Users/hyukjoon/Downloads/CSA/src/main/resources/assets/csa/textures/item/card_{num}.png"]
    output_paths = [f"/Users/hyukjoon/Downloads/CSA/src/main/resources/assets/csa/textures/item/card_{num}.png"]

    # Target size (e.g., 512x512)
    target_size = 560

    # Process each image
    for img_path, out_path in zip(image_paths, output_paths):
        add_padding(img_path, out_path, target_size)
    print(f"{num} done \n")
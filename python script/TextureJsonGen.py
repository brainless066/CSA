import os
import json

# Define the directory where the JSON files will be saved
directory = "/Users/hyukjoon/Downloads/CSA/src/main/resources/assets/csa/models/item"

# Ensure the directory exists
os.makedirs(directory, exist_ok=True)

# Define the base content for the JSON files
base_content = {
    "parent": "item/generated",
    "textures": {
        "layer0": ""
    }
}

# Generate JSON files for cards 001 to 151
for i in range(1, 152):
    # Create the file name with leading zeros
    file_name = f"card_{i:03}.json"

    # Update the texture path
    base_content["textures"]["layer0"] = f"csa:item/card_{i:03}"

    # Create the full path for the JSON file
    file_path = os.path.join(directory, file_name)

    # Write the JSON content to the file
    with open(file_path, 'w') as json_file:
        json.dump(base_content, json_file, indent=2)

print("JSON files created successfully.")

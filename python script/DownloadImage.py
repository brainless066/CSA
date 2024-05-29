# Download image from url
for x in range(4, 152):
    num = x

    #make num 3 digits (adding 0)
    num = str(num).zfill(3)

    url = f"https://cards.image.pokemonkorea.co.kr/data/wmimages/SV/SV2a/SV2a_{num}.png?w=400"

    #download image
    import requests
    response = requests.get(url)

    #save image at /Users/hyukjoon/Downloads/CSA/src/main/resources/assets/csa/textures/item
    with open(f"/Users/hyukjoon/Downloads/CSA/src/main/resources/assets/csa/textures/item/card_{num}.png", "wb") as file:
        file.write(response.content)

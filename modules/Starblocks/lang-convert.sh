#!/usr/bin/bash
cd "$(dirname "$0")"
OUT='common/assets/minecraft/lang/en_us.json'
SRC='../../reference/MC14.en_us.json'
cat "${SRC}" | grep -E "{|Bookshelf|Anvil|Furnace|Brewing|[Pp]otion|Blaze Rod|Blaze Powder|[Ee]nchant|Lapis|Smoker|Table|[Bb]ook|Smooth Stone|Stone Pressure Plate|TNT|Apple|Bed|[Rr]edstone|Sea Lantern|Painting|Coal|Charcoal|Torch|_helmet|_chestplate|_leggings|_boots" | grep -E --invert-match "effect\.minecraft\.wither|[Rr]ender|death\.attack|gui\.recipebook|bedrock|Parrot" | sed "s/Chipped Anvil/Aging Repair-Bot/" | sed "s/Anvil/Repair-Bot/" | sed "s/Bookshelf/Data Core/" | sed "s/Table/Station/" | sed "s/Blaze Rod/Nanite Reactor/" | sed "s/Blaze Powder/Nanite Dust/" | sed "s/Potion/Nanite Serum/" | sed "s/Brewing Stand/Nanite Factory/" | sed "s/%s Lapis Lazuli/%s Catalite Crystals/" | sed "s/Lapis Lazuli/Catalite Crystal/" | sed "s/Enchanting Station/Upgrade Station/" | sed "s/Enchanted Book/Upgrade Kit/" | sed "s/Enchanting/Upgrading/" | sed "s/Enchanted/Upgraded/" | sed "s/Enchantment/Upgrade/" | sed "s/Enchant/Upgrade/" | sed "s/Smooth Stone/Silicon Panel/" | sed "s/Turtle/Turdle/" | sed "s/Disenchant/Reset/" | sed "s/Smite/Anti-Cyborg/" | sed "s/Bane of Arthropods/Anti-Killbot/" | sed "s/ enchantment/ upgrade/" | sed "s/Fletching/Munitions/" | sed "s/Smoker/Oven/" | sed "s/Book/Tablet/" | sed "s/ book/ tablet/" | sed "s/Primed TNT/Overloaded Reactor/" | sed "s/TNT/Unstable Reactor/" | sed "s/Apple/Starfruit/" | sed "s/Quill/Stylus/" | sed "s/Curse of /Glitch: /" | sed "s/Bed/Cloning Bed/" | sed "s/Redstone Dust/Redstone Wire/" | sed "s/Sea Lantern/Star Light/" | sed "s/Stone Pressure Plate/Pressure Plate/" | sed "s/Painting/View Screen/" | sed "s/Charcoal/Fuel Cell/" | sed "s/Block of Coal/Nuclear Reactor/" | sed "s/Coal/Thorium/" | sed "s/Torch/Light/" | sed "s/Redstone Light/Redstone Torch/" | sed "s/Redstone Wall Light/Redstone Wall Torch/" | sed "s/Leather Cap/Spacesuit Helmet/" | sed "s/Leather Tunic/Spacesuit Torso/" | sed "s/Leather Pants/Spacesuit Pants/" | sed "s/Leather Boots/Spacesuit Boots/" | sed "s/Chainmail/Cybersuit/" | sed "s/Iron Helmet/Armored Spacesuit Helmet/" | sed "s/Iron Chestplate/Armored Spacesuit Torso/" | sed "s/Iron Leggings/Armored Spacesuit Leggings/" | sed "s/Iron Boots/Armored Spacesuit Boots/" | sed "s/Diamond Helmet/Forcefield Helmet/" | sed "s/Diamond Chestplate/Forcefield Chestplate/" | sed "s/Diamond Leggings/Forcefield Leggings/" | sed "s/Diamond Boots/Forcefield Boots/" | sed "s/Leather armor/Spacesuit/" | sed "s/Leather/Pleather/" > ${OUT}
echo '"_":"_" }' >> ${OUT}

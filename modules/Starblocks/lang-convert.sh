#!/usr/bin/bash
cd "$(dirname "$0")"
OUT='common/assets/minecraft/lang/en_us.json'
SRC='../../reference/MC14.en_us.json'
cat "${SRC}" | grep -E "{|Bookshelf|Anvil|Furnace|Brewing|[Pp]otion|Blaze Rod|Blaze Powder|[Ee]nchant|Lapis|Smoker|Table|[Bb]ook|TNT|Apple|Bed|[Rr]edstone|Sea Lantern" | grep -E --invert-match "effect\.minecraft\.wither|[Rr]ender|death\.attack|gui\.recipebook|bedrock|Parrot" | sed "s/Chipped Anvil/Aging Repair-Bot/" | sed "s/Anvil/Repair-Bot/" | sed "s/Bookshelf/Data Core/" | sed "s/Table/Station/" | sed "s/Blaze Rod/Nanite Reactor/" | sed "s/Blaze Powder/Nanite Dust/" | sed "s/Potion/Nanite Serum/" | sed "s/Brewing Stand/Nanite Factory/" | sed "s/%s Lapis Lazuli/%s Catalite Crystals/" | sed "s/Lapis Lazuli/Catalite Crystal/" | sed "s/Enchanting Station/Upgrade Station/" | sed "s/Enchanted Book/Upgrade Kit/" | sed "s/Enchanting/Upgrading/" | sed "s/Enchanted/Upgraded/" | sed "s/Enchantment/Upgrade/" | sed "s/Enchant/Upgrade/" | sed "s/Turtle/Turdle/" | sed "s/Disenchant/Reset/" | sed "s/Smite/Anti-Cyborg/" | sed "s/Bane of Arthropods/Anti-Killbot/" | sed "s/ enchantment/ upgrade/" | sed "s/Fletching/Munitions/" | sed "s/Smoker/Oven/" | sed "s/Book/Tablet/" | sed "s/ book/ tablet/" | sed "s/Primed TNT/Overloaded Reactor/" | sed "s/TNT/Unstable Reactor/" | sed "s/Apple/Starfruit/" | sed "s/Quill/Stylus/" | sed "s/Curse of /Glitch: /" | sed "s/Bed/Cloning Bed/" | sed "s/Redstone Dust/Redstone Wire/" | sed "s/Sea Lantern/Star Light/" > ${OUT}
echo '"_":"_" }' >> ${OUT}

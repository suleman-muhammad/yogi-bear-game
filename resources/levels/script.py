import  os

rangers_data = [
    # Level 1 (0 Rangers)
    "",

    # Level 2 (1 com.game.yogibear.entities.Ranger) - Central horizontal patrol
    "300,200,horizontal,1",

    # Level 3 (2 Rangers) - Top left and bottom right areas
    """100,100,horizontal,1
450,300,horizontal,-1""",

    # Level 4 (3 Rangers) - Mixing vertical and horizontal
    """150,100,vertical,1
300,200,horizontal,-1
450,300,vertical,-1""",

    # Level 5 (4 Rangers) - Framing the outer edges
    """60,60,horizontal,1
500,60,vertical,1
500,340,horizontal,-1
60,340,vertical,-1""",

    # Level 6 (5 Rangers) - Defined lanes amidst denser hurdles
    """100,80,horizontal,1
450,80,horizontal,-1
280,150,vertical,1
100,320,horizontal,1
450,320,horizontal,-1""",

    # Level 7 (6 Rangers) - The "Gauntlet" vertical patrol
    """150,60,vertical,1
250,60,vertical,1
350,60,vertical,1
150,340,vertical,-1
250,340,vertical,-1
350,340,vertical,-1""",

    # Level 8 (7 Rangers) - Scattered patrols
    """60,100,horizontal,1
60,200,horizontal,1
60,300,horizontal,1
500,150,horizontal,-1
500,250,horizontal,-1
280,60,vertical,1
280,340,vertical,-1""",

    # Level 9 (8 Rangers) - Tight edge patrols
    """80,60,horizontal,1
200,60,horizontal,1
320,60,horizontal,1
440,60,horizontal,1
80,340,horizontal,-1
200,340,horizontal,-1
320,340,horizontal,-1
440,340,horizontal,-1""",

    # Level 10 (9 Rangers) - The Grid patrol
    """100,100,horizontal,1
300,100,vertical,1
500,100,horizontal,-1
100,200,vertical,-1
300,200,horizontal,1
500,200,vertical,1
100,300,horizontal,1
300,300,vertical,-1
500,300,horizontal,-1"""
]


baskets_data = [
    # Level 1 (2 Baskets)
    """150,200
450,200""",

    # Level 2 (4 Baskets) - Corners
    """100,100
500,100
100,300
500,300""",

    # Level 3 (6 Baskets) - Top and bottom rows
    """100,150
300,150
500,150
100,250
300,250
500,250""",

    # Level 4 (8 Baskets) - Scattered
    """80,80
220,80
380,80
500,80
80,320
220,320
380,320
500,320""",

    # Level 5 (10 Baskets) - Wide rows
    """60,100
180,100
300,100
420,100
500,100
60,300
180,300
300,300
420,300
500,300""",

    # Level 6 (12 Baskets) - Grid pockets
    """100,80
233,80
366,80
480,80
100,200
233,200
366,200
480,200
100,320
233,320
366,320
480,320""",

    # Level 7 (14 Baskets) - Tight rows
    """60,120
130,120
210,120
300,120
390,120
470,120
500,120
60,280
130,280
210,280
300,280
390,280
470,280
500,280""",

    # Level 8 (16 Baskets) - Grid
    """100,90
230,90
360,90
490,90
100,170
230,170
360,170
490,170
100,250
230,250
360,250
490,250
100,330
230,330
360,330
490,330""",

    # Level 9 (18 Baskets) - Compressed grid
    """70,100
150,100
230,100
310,100
390,100
470,100
70,200
150,200
230,200
310,200
390,200
470,200
70,300
150,300
230,300
310,300
390,300
470,300""",

    # Level 10 (20 Baskets) - Squeezed into available spots
    """80,70
180,70
280,70
380,70
480,70
80,150
180,150
280,150
380,150
480,150
80,230
180,230
280,230
380,230
480,230
80,310
180,310
280,310
380,310
480,310"""
]


hurdles_data = [
    # Level 1 (2 Hurdles) - Simple center blockers
    """250,200,mountain,10
350,200,mountain,10""",

    # Level 2 (4 Hurdles) - Dividing the screen into quadrants
    """300,100,tree,5
300,300,tree,5
150,200,mountain,10
450,200,mountain,10""",

    # Level 3 (6 Hurdles) - Creating lanes
    """100,100,bush,5
200,100,bush,5
400,100,bush,5
500,100,bush,5
300,50,bush,5
300,350,bush,5""",

    # Level 4 (8 Hurdles) - A central cross pattern
    """200,200,tree,5
250,200,tree,5
300,200,tree,5
350,200,tree,5
400,200,tree,5
300,100,tree,5
300,150,tree,5
300,250,tree,5""",

    # Level 5 (10 Hurdles) - Dense borders with a central gap
    """50,100,bush,5
50,150,bush,5
50,200,bush,5
50,250,bush,5
50,300,bush,5
500,100,bush,5
500,150,bush,5
500,200,bush,5
500,250,bush,5
500,300,bush,5""",

    # Level 6 (12 Hurdles) - Two large vertical pillars forming three lanes
    """200,80,mountain,10
200,140,mountain,10
200,200,mountain,10
200,260,mountain,10
200,320,mountain,10
400,80,mountain,10
400,140,mountain,10
400,200,mountain,10
400,260,mountain,10
400,320,mountain,10
300,50,smallmount,5
300,350,smallmount,5""",

    # Level 7 (14 Hurdles) - Scattered small obstacles creating a maze
    """100,100,bush,5
180,100,bush,5
260,100,bush,5
340,100,bush,5
420,100,bush,5
100,200,tree,5
180,200,tree,5
260,200,mountain,10
340,200,tree,5
420,200,tree,5
100,300,bush,5
180,300,bush,5
260,300,bush,5
340,300,bush,5
420,300,bush,5""",

    # Level 8 (16 Hurdles) - Four distinct "rooms"
    """150,50,tree,5
150,100,tree,5
150,150,tree,5
150,250,tree,5
150,300,tree,5
150,350,tree,5
450,50,tree,5
450,100,tree,5
450,150,tree,5
450,250,tree,5
450,300,tree,5
450,350,tree,5
250,200,mountain,10
300,200,mountain,10
350,200,mountain,10
300,50,smallmount,5""",

    # Level 9 (18 Hurdles) - A dense 3x3 grid of larger obstacles
    """120,100,mountain,10
240,100,mountain,10
360,100,mountain,10
480,100,mountain,10
120,200,mountain,10
240,200,mountain,10
360,200,mountain,10
480,200,mountain,10
120,300,mountain,10
240,300,mountain,10
360,300,mountain,10
480,300,mountain,10
60,150,bush,5
60,250,bush,5
500,150,bush,5
500,250,bush,5
300,50,tree,5
300,350,tree,5""",

    # Level 10 (20 Hurdles) - A tight maze of small bushes
    """80,80,bush,5
160,80,bush,5
240,80,bush,5
320,80,bush,5
400,80,bush,5
480,80,bush,5
80,160,bush,5
160,160,bush,5
240,160,bush,5
320,160,bush,5
400,160,bush,5
480,160,bush,5
80,240,bush,5
160,240,bush,5
240,240,bush,5
320,240,bush,5
400,240,bush,5
480,240,bush,5
280,320,mountain,10
320,320,mountain,10"""
]


for i in range(1,11):
    dir_name = f"level{i}"
    file_path = os.path.join(dir_name, "baskets.txt")
    with open(file_path, "w") as file:
        file.write(baskets_data[i-1])
    file_path = os.path.join(dir_name, "rangers.txt")
    with open(file_path,"w") as f:
        f.write(rangers_data[i-1])
    file_path = os.path.join(dir_name, "hurdles.txt")
    with open(file_path,"w") as f:
        f.write(hurdles_data[i-1])
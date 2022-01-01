import Control.Monad
import Data.List.Split
import Lens.Micro

type Point = (Int, Int)
data Line = Line Point Point
  deriving Show

main :: IO ()
main = do
  input <- getInput
  putStrLn $ "Part 1: " <> show (answer pointsHV input)
  putStrLn $ "Part 2: " <> show (answer pointsHVD input)

answer :: (Line -> [Point]) -> [Line] -> Int
answer points ls = sum . map (length . filter (>= 2)) . 
  foldr (\(x, y) board -> board & ix y %~ (\row -> row & ix x +~ 1)) emptyBoard $ ps
  where ps = concatMap points ls
        (maxX, maxY) = foldr (\(x, y) (mx, my) -> (max x mx, max y my)) (0, 0) ps
        emptyBoard = replicate (maxX + 1) (replicate (maxY + 1) 0)

-- For part 1
pointsHV :: Line -> [Point]
pointsHV (Line (x1, y1) (x2, y2))
  | x1 == x2 = [(x1, y) | y <- [min y1 y2..max y1 y2]]
  | y1 == y2 = [(x, y1) | x <- [min x1 x2..max x1 x2]]
  | otherwise = []

  -- For part 2
pointsHVD :: Line -> [Point]
pointsHVD (Line (x1, y1) (x2, y2))
  | x1 == x2 = [(x1, y) | y <- [min y1 y2..max y1 y2]]
  | y1 == y2 = [(x, y1) | x <- [min x1 x2..max x1 x2]]
  | otherwise = zip (x1 `to` x2) (y1 `to` y2)
      where a `to` b = [a, a + signum (b - a)..b]

pprint :: [[Int]] -> IO ()
pprint ps = do
  let doTheThing xs = putStr (concatMap (\x -> if x == 0 then "." else show x) xs) >> putStrLn ""
  mapM_ doTheThing ps

getInput :: IO [Line]
getInput = parseFile "input.txt"

getTest :: IO [Line]
getTest = parseFile "test.txt"

parseFile :: FilePath -> IO [Line]
parseFile fileName = do 
  regels <- lines <$> readFile fileName
  let toPoint [x, y] = (read x, read y)
      toLine [p1, p2] = Line p1 p2
  return $ map (toLine . map (toPoint . splitOn ",") . splitOn " -> ") regels

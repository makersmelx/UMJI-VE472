
all <- c(112.9249398591139,56.39903430238407, 88.0075723830735, 152.79684263161656, 91.90549834238556,62.03700669782682)
png(file = "ex3.2.png")

barplot(all,
main = "Annual average temperature (\u00B0C)",
xlab = "Temperature (\u00B0C)",
ylab = "Continent",
names = c("AS", "EU", "AF", "OC", "NA", "SA"),
las = 2,
col = c("grey"),
border = "black",
)

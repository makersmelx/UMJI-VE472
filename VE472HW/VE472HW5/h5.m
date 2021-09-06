clear; clearvars;
matrices = cell(1, 100);
for i = 1 : 100
    matrices{i} = rand(1000,100);
end
disp('a)')
tic;
for i = 1 : 100
    X = matrices{i};
    [U,S,V] = svd(X);
end
toc;
disp('b)')
tic;
for i = 1 : 100
    X = matrices{i};
    [U,S,V] = svd(X');
end
toc;
disp('c)')
tic;
for i = 1 : 100
    X = matrices{i};
    e = eig(X*(X'));
end
toc;
disp('d)')
tic;
for i = 1 : 100
    X = matrices{i};
    e = eig((X')*X);
end
toc;
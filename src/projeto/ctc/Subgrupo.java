package projeto.ctc;

import java.util.List;

public class Subgrupo {
	int [] elementos;
	int tamanho = 0;
	
	Subgrupo (int[] vetor, int n){
		elementos = vetor;
		tamanho = n;
	}
	
	boolean isClosed(Tabela matrix){
		int i;
		int j;
		for(i = 0; i < tamanho; i++){
			if(elementos[i]==1){
				for(j = 0; j < tamanho; j++){
					if(elementos[j]==1){
						int index = matrix.getPosition(matrix.tabela[i][j]);
						if(elementos[index]==0){
							return false;	
						}
					}
				}
			}
		}
		return true;
	}
	
	boolean hasSubInverses(Tabela matrix){
		for(int i = 0; i < tamanho; i++){
			if(elementos[i]==1){
				int index = matrix.getPosition(matrix.inversos[i]);
				if(elementos[index]==0)
					return false;
			}
		}
		return true;
	}
	
	char [] tansformeToCharVetor(Tabela matrix){
		int counter=0;
		for(int j=0; j < tamanho; j++)
			if(elementos[j]==1)
				counter++;
		char[] vetor = new char[counter];
		int j = 0;
		for(int i=0; i<tamanho; i++){
			if(elementos[i]==1){
				vetor[j] = matrix.tabela[0][i];
				j++;
			}
		}
		return vetor;
	}
}

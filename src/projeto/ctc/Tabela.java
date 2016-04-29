package projeto.ctc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tabela {
	char [][] tabela;
	char [] inversos;
	int dimension = 0;
	List <int[]> possibleGroups = new ArrayList<int[]>(); 
	List <char[]> SubGroups = new ArrayList<char[]>(); 
	Tabela (int fileNumber){
		Scanner s = null;
		try{
			dimension = getDimension(fileNumber);
			tabela = new char[dimension][dimension];
			s = new Scanner(new BufferedReader(new FileReader("table "+fileNumber+".txt")));
			String str = new String();
			int i=0;
			while(s.hasNextLine()){
                str = s.nextLine();
                tabela[i] = str.toCharArray();
                i++;
            }	
		}catch(Exception e){
			System.out.println("could not find file");
		}
	}
	
	int getDimension(int fileNumber){
		Scanner s = null;
		if(dimension==0){
			try{
				s = new Scanner(new BufferedReader(new FileReader("table "+ fileNumber +".txt")));
				String str = s.nextLine();
				dimension = str.length();
				s.close();
			}catch(Exception e){
				System.out.println("could not find file");
			}
		}
		return dimension;
	}
	
	void makeListPossibleSubgroups(){
		int aux = (int) Math.pow(2,dimension-1);
		for(int j = 0; j < aux; j++){
			//System.out.println(j+"=");
			int [] vetor = new int [dimension];
			vetor[0]=1;
			int d = j;
			for(int i = 0; i < (dimension-1); i++){
				int b = d % 2;
				vetor[dimension-1-i] = b;
				d = d/2;
			}
			/*System.out.println("");
			for(int k =0; k < dimension; k++){
				System.out.print(vetor[k]);
			}
			System.out.println("");*/
			possibleGroups.add(vetor);
		}
	}
	
	void printTable (){
		for (int i = 0; i < dimension; i++) {
		    for (int j = 0; j < dimension; j++) {
		        System.out.print(tabela[i][j]+" ");
		    }
		    System.out.print("\n");
		}
	}
	
	int getPosition(char element){
		int i;
		for(i=0 ;i < dimension; i++ ){
			if(element == tabela[0][i])
				return i;
		}
		return -1;
	}
	
	boolean isClosed(){
		for(int i = 0; i < dimension; i++)
			for(int j = 0; j < dimension; j++)
				if(getPosition(tabela[i][j])== -1)
					return false;
		return true;
	}
	
	boolean verifyAssociativity( ){
		int p,q;
		for(int i = 0; i < dimension; i++)
			for(int j = 0; j < dimension; j++)
				for(int k = 0; k < dimension; k++){
					p = getPosition(tabela[i][j]);
					q = getPosition(tabela[j][k]);
					if(tabela[p][k]!= tabela[i][q])
						return false;
				}
		return true;
	}
	
	boolean hasInverses(){
		inversos = new char[dimension];
		for(int i = 0; i < dimension; i++)
			for(int j = 0; j < dimension; j++){
				if(tabela[i][j]=='1'){
					if(tabela[j][i]=='1')
						inversos[i]=tabela[0][j];
					else return false;
				}
			}
		return true;
	}
	
	public static void main(String[] args) {
		int m;
		for( m = 1; m <= 8; m++){
			System.out.println("/*---------------Table " +m+"---------------");	
			Tabela matrix = new Tabela(m);
			matrix.printTable();
			System.out.println("");
			boolean isclosed = matrix.isClosed();
			System.out.println("O grupo é fechado? " + isclosed);
			boolean isAssociative;
			if(isclosed==true){
				isAssociative = matrix.verifyAssociativity();
				System.out.println("O grupo é associativo? " + isAssociative);
				boolean hasinverses = matrix.hasInverses();
				System.out.println("O grupo tem todos os inversos? " + hasinverses);
				if(hasinverses==true){
					System.out.println("");
					for(int k = 0; k < matrix.dimension; k++)
						System.out.print(matrix.tabela[0][k]+" ");
					System.out.println("");
					for(int k = 0; k < matrix.dimension; k++)
						System.out.print(matrix.inversos[k]+" ");
					System.out.println("");
				}
				if(isclosed==true && isAssociative==true && hasinverses==true)
				{
					matrix.makeListPossibleSubgroups();
					int t = (int) Math.pow(2,matrix.dimension-1);
					for(int k = 0; k < t; k++){
						Subgrupo subset = new Subgrupo(matrix.possibleGroups.get(k),matrix.dimension);
						if(subset.isClosed(matrix)==true&&subset.hasSubInverses(matrix)==true)
						{
							matrix.SubGroups.add(subset.tansformeToCharVetor(matrix));
						}
					}
					System.out.println("");
					if(matrix.SubGroups.isEmpty()==true)
						System.out.println("Não há subgrupos");
					else{
						System.out.println("Os subgrupos são:");
						for (char[] aux : matrix.SubGroups)
							System.out.println(aux);
					}
				}
			}
			
		}
		System.out.println("");
		System.out.println("Fim de Programa.");
	}
	
}

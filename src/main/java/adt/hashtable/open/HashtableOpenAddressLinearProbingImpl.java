package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		initiateInternalTable(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
	}

	@Override
	public void insert(T element) {
		if (isFull()) {
			throw new HashtableOverflowException();
		}
		
		if (element != null) {
			if (search(element) != null) {
				return;
			}
			
			int probe = 0;
			int hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			
			while (probe < table.length - 1) {
				if (table[hashIndex] == null || table[hashIndex] instanceof DELETED) {
					elements++;
					table[hashIndex] = element;
					break;
				} else {
					probe++;
					COLLISIONS++;
					hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
				}
			}
		}
		
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			int hashIndex = indexOf(element);
			
			if (hashIndex >= 0) {
				table[hashIndex] = new DELETED();
				elements--;
			}
		}
	}

	@Override
	public T search(T element) {
		if (element != null) {
			int probe = 0;
			int hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			
			while (probe < table.length - 1) {
				if (table[hashIndex] == null) {
					return null;
				}
				
				if (table[hashIndex].equals(element)) {
					return element;
				} else {
					probe++;
					hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
				}
			}
		}
		
		return null;
	}

	@Override
	public int indexOf(T element) {
		if (element != null) {
			int probe = 0;
			int hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			
			while (probe < table.length - 1) {
				if (table[hashIndex] == null) {
					return -1;
				}
				
				if (table[hashIndex].equals(element)) {
					return hashIndex;
				} else {
					probe++;
					hashIndex = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
				}
			}
		}
		
		return -1;
	}
	
}